package com.grabit.app.controller;

import com.grabit.app.dto.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8000")
public class HomeController {

    @GetMapping
    public ResponseEntity<TokenDTO> home(HttpServletRequest request, Authentication principal) {
        if (principal == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        OidcUser user = (OidcUser) principal.getPrincipal();
        TokenDTO token = new TokenDTO(user.getIdToken().getTokenValue());
        return ResponseEntity.ok(token);
    }
}
