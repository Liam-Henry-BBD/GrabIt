package com.grabit.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfToken;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
    private String token;
    private String csrf;
}
