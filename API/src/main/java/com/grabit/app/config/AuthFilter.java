package com.grabit.app.config;

import com.grabit.app.model.Auth2User;
import com.grabit.app.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import java.io.IOException;

import java.util.*;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Controller;

@Controller
@WebFilter("/*")
public class AuthFilter implements Filter {
    private JwtDecoder jwtDecoder;

    private final UserService userService;


    public AuthFilter(UserService userService) {
        this.userService = userService;
        this.jwtDecoder = JwtDecoders.fromIssuerLocation("https://accounts.google.com");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (!httpRequest.getRequestURI().startsWith("/api")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = httpRequest.getHeader("Authorization");
        if (token == null) {
            sendHttpResponse(httpResponse, "UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
            return;
        }
        try {

            token = token.replace("Bearer ", "").trim();
            if (jwtDecoder == null) {
                jwtDecoder = JwtDecoders.fromIssuerLocation("https://accounts.google.com");
            }
            Jwt jwt = jwtDecoder.decode(token);
            Map<String, Object> claims = jwt.getClaims();
            String email = (String) claims.get("email");
            String emailSplit = email.split("@")[0];

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            Auth2User user = new Auth2User(emailSplit, (String) claims.get("sub"));
            OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(user, authorities, "google");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch ( JwtException | IllegalArgumentException | AuthenticationException e) {
            sendHttpResponse(httpResponse, e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    public void sendHttpResponse(HttpServletResponse httpResponse, String message, HttpStatus status)
            throws IOException {
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType("application/json");
        JSONObject obj = new JSONObject();
        obj.put("status", status.value());
        obj.put("message", message);
        httpResponse.getWriter().write(String.valueOf(obj));
    }

}
