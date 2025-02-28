package com.grabit.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Auth2User implements OAuth2User{
    private OAuth2User oAuth2User;
    private OAuth2UserRequest userRequest;



    public OAuth2User getOAuth2User() {
        return oAuth2User;
    }

    public OAuth2UserRequest getUserRequest() {
        return userRequest;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return "";
    }
}
