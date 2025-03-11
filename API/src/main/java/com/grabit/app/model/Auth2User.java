package com.grabit.app.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Auth2User implements OAuth2User{

    private String id;
    private String login;

    public Auth2User(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(responseBody);
        this.login = node.get("login").asText();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "login", login
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return login;
    }
}
