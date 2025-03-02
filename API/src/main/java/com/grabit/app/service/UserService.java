package com.grabit.app.service;

import java.time.LocalDate;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grabit.app.model.User;
import com.grabit.app.repository.UserRepository;

@Service
public class UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveOrUpdateUser(String gitHubID) {
        User user = new User();
        user.setGitHubID(gitHubID);
        user.setJoinedAt(LocalDate.now());

        User newUser = userRepository.findByGitHubID(user.getGitHubID());
        if (newUser != null) {
            return;
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String githubID = oAuth2User.getAttribute("login").toString();
        this.saveOrUpdateUser(githubID);
        return oAuth2User;
    }
}
