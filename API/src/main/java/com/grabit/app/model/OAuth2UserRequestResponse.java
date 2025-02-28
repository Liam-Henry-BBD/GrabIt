package com.grabit.app.model;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserRequestResponse {
    private OAuth2User oAuth2User;
    private OAuth2UserRequest userRequest;

    public OAuth2UserRequestResponse(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        this.oAuth2User = oAuth2User;
        this.userRequest = userRequest;
    }

    public OAuth2User getOAuth2User() {
        return oAuth2User;
    }

    public OAuth2UserRequest getUserRequest() {
        return userRequest;
    }
}
