package com.grabit.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Auth2User implements OAuth2User{

    private String id;
    private String username;

    public Auth2User(String emailSplit, String sub) {
        this.id = sub;
        this.username = emailSplit;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "username", username
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return username;
    }
}
