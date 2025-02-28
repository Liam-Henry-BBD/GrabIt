package com.grabit.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grabit.app.model.Auth2User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@NoArgsConstructor
@Component
@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter");
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
            String responseBody  = getUserDetails(token);
            if (responseBody != null) {
                attachPrincipalToSecurityContext(responseBody, httpRequest);
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                sendHttpResponse(httpResponse, "UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
            }
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUserDetails(String token) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest
                .newBuilder().uri(new URI("https://api.github.com/user"))
                .header("Authorization", "Bearer " + token)
                .GET().build();

        HttpResponse<String> send = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (send.statusCode() != HttpStatus.OK.value()) {
            return null;
        }
        return send.body();
    }

    public void attachPrincipalToSecurityContext(String userObject, HttpServletRequest request) throws JsonProcessingException {
        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(
                new Auth2User(userObject),
                null,
                "github"
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void sendHttpResponse(HttpServletResponse httpResponse, String message, HttpStatus status) throws IOException {
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType("application/json");
        JSONObject obj = new JSONObject();
        obj.put("status", status);
        obj.put("message", message);
        httpResponse.getWriter().write(String.valueOf(obj));
    }

}
