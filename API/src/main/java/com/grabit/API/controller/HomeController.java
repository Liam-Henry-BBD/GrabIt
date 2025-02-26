package com.grabit.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class HomeController {
    @GetMapping("/me")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes(); // Returns GitHub user details
    }
};
