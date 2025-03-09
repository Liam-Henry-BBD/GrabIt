package com.grabit.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login/**", "/oauth2/**", "/favicon.ico", "/").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login/**", "/oauth2/**", "/favicon.ico", "/")
                        .requireCsrfProtectionMatcher(request -> {
                            String authorizationHeader = request.getHeader("Authorization");
                            return !(authorizationHeader != null && authorizationHeader.startsWith("Bearer "));
                        }));
        return http.build();
    }

}
