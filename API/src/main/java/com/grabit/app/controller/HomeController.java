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
                    <h2>Available browser endpoints:</h2>
                    <ul>
                        <li>Get your user info: <code>/me</code></li>
                        <li>Get all projects: <code>/api/projects</code></li>
                        <li>Get a project by id: <code>/api/projects/{projectID}</code></li>
                        <li>Get all collaborators of a project: <code>/api/projects/{projectID}/collaborators</code></li>
                        <li>Get the leaderboard of a project: <code>/api/projects/{projectID}/leaderboard</code></li>
                        <li>Get all tasks of a project: <code>/api/projects/{projectID}/tasks</code></li>
                        <li>Get a task by id: <code>/api/tasks/{taskID}</code></li>
                        <li>Get all collaborators of a task: <code>/api/tasks/{taskID}/collaborators</code></li>
                        <li>Get the status of a task: <code>/api/tasks/{taskID}/statuses</code></li>
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
