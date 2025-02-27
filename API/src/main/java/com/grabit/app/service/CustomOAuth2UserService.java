package com.grabit.app.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    private final jakarta.servlet.http.HttpSession httpSession;

    public CustomOAuth2UserService(UserService userService, jakarta.servlet.http.HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @Override
    @Transactional
    public org.springframework.security.oauth2.core.user.OAuth2User loadUser(OAuth2UserRequest userRequest) {
        org.springframework.security.oauth2.core.user.OAuth2User oAuth2User = super.loadUser(userRequest);

        String githubID = oAuth2User.getAttribute("login").toString();

        userService.saveOrUpdateUser(githubID);

        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        httpSession.setAttribute("github_access_token", accessToken.getTokenValue());

        return oAuth2User;
    }
}
