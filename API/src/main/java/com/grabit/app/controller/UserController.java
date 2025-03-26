package com.grabit.app.controller;


import com.grabit.app.dto.UserDTO;
import com.grabit.app.service.UserService;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDTO> me(Authentication principal) {
        OidcUser user = (OidcUser) principal.getPrincipal();
        Integer userID = userService.getAuthenticatedUser(principal).getUserID();
        UserDTO userDTO = new UserDTO(
            user.getEmail(), user.getFullName(), user.getPicture(), user.getEmailVerified(), userID
        );
        return ResponseEntity.ok(userDTO);
    }
}
