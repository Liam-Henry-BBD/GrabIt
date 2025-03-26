package com.grabit.app.controller;


import com.grabit.app.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8000")
public class UserController {

    @GetMapping("/validate")
    public ResponseEntity<UserDTO> me(Authentication principal) {
        OidcUser user = (OidcUser) principal.getPrincipal();
        UserDTO userDTO = new UserDTO(
            user.getEmail(), user.getFullName(), user.getPicture(), user.getEmailVerified()
        );
        return ResponseEntity.ok(userDTO);
    }
}
