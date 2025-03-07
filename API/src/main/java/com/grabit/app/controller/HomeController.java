package com.grabit.app.controller;

import com.grabit.app.dto.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<TokenDTO> home(HttpServletRequest request, Authentication principal) {
        OidcUser user = (OidcUser) principal.getPrincipal();
        String csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        TokenDTO token = new TokenDTO(user.getIdToken().getTokenValue(), csrf);
        return ResponseEntity.ok(token);
    }
}
