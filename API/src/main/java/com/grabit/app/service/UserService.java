package com.grabit.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grabit.app.dto.UserSearchDTO;
import com.grabit.app.exceptions.NotFound;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.Authentication;
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

    public User getAuthenticatedUser(Authentication authentication) {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String username = oidcUser.getClaims().get("email").toString().split("@")[0];
        return userRepository.findByGitHubID(username);
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String githubID = Optional.ofNullable(oAuth2User.getAttribute("login"))
                .map(Object::toString)
                .orElseThrow(() -> new NotFound("Google ID not found"));
        this.saveOrUpdateUser(githubID);
        return oAuth2User;
    }

    public List<UserSearchDTO> searchUserNames(String query) {
        List<User> users = userRepository.findByGitHubIDContainingIgnoreCase(query);
        return users.stream()
                .map(user -> new UserSearchDTO(user.getGitHubID(), user.getUserID()))
                .collect(Collectors.toList());
    }

}
