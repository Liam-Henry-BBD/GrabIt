package com.grabit.app.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grabit.app.model.User;
import com.grabit.app.repository.UserRepository;

@Service
public class UserService {
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
}
