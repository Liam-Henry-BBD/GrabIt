package com.grabit.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String welcome() {
        return """
                <html>
                <body>
                    <h1>Welcome to Grabit API</h1>
                    <h2>Available Endpoints:</h2>
                    <ul>
                        <li>Get user info: <code>/me</code></li>
                        <li>Get all projects: <code>/api/projects</code></li>
                        <li>Get project by id: <code>/api/projects/{id}</code></li>
                        <li>Get all tasks of a project: <code>/api/projects/{id}/tasks</code></li>
                        <li>Get all collaborators of a project: <code>/api/projects/{id}/collaborators</code></li>
                        <li>Get task by id: <code>/api/tasks/{id}</code></li>
                    </ul>
                </body>
                </html>
                """;
    }

    @GetMapping("/me")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
};
