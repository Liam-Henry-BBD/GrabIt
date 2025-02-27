package com.grabit.app.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {

    public String getGitHubAccessToken() {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        if (authenticationToken != null && authenticationToken.isAuthenticated()) {
            OAuth2AuthorizedClient authorizedClient = (OAuth2AuthorizedClient) authenticationToken.getCredentials();

            if (authorizedClient != null) {
                System.out.println(authorizedClient.getAccessToken().getTokenValue());
                return authorizedClient.getAccessToken().getTokenValue();
            }
        }

        return null;
    }
}
