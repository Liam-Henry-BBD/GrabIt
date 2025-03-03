package com.grabit.app.controller;

import com.grabit.app.dto.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public HomeController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping
    public ResponseEntity<TokenDTO> home(@AuthenticationPrincipal OAuth2User principal,
                                         OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName());

        if(principal != null) {
            if (client != null && client.getAccessToken() != null) {
                return ResponseEntity.ok(new TokenDTO(client.getAccessToken().getTokenValue()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/docs")
    public String welcome() {
        return """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <style>
                             body {
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                margin: 0;
                                background-color: #f4f4f4;
                            }

                            .content-box {
                                background-color: white;
                                padding: 20px;
                                border-radius: 10px;
                                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.9);
                                width: 80%;
                                max-width: 800px;
                            }

                            h1 {
                                text-align: center;
                                color: #333;
                            }

                            h2 {
                                color: #555;
                            }

                            ul {
                                list-style-type: none;
                                padding: 0;
                            }

                            li {
                                margin: 10px 0;
                            }

                            code {
                                background-color:#0000;
                                padding: 2px 5px;
                                border-radius: 3px;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="content-box">
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
                        </div>
                    </body>
                    </html>
                """;
    }

    @GetMapping("/me")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
}
