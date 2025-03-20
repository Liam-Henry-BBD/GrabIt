package com.grabit.app.config;

import com.grabit.app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final UserService userService;
    public SecurityConfig(AuthFilter authFilter, UserService userService) {
        this.authFilter = authFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/oauth2/**", "/favicon.ico", "/").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(auth ->
                        auth.userInfoEndpoint(info ->
                                info.oidcUserService(service -> {
                                    String email = (String) service.getIdToken().getClaims().get("email");
                                    String emailSplit = email.split("@")[0];
                                    userService.saveOrUpdateUser(emailSplit);
                                    return new DefaultOidcUser(null, service.getIdToken());
                                })))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login/**", "/oauth2/**", "/favicon.ico", "/")
                        .requireCsrfProtectionMatcher(request -> {
                            String authorizationHeader = request.getHeader("Authorization");
                            return !(authorizationHeader != null && authorizationHeader.startsWith("Bearer "));
                        }));
        return http.build();
    }
}
