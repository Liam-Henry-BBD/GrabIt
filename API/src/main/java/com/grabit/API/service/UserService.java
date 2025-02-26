package com.grabit.api.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grabit.api.model.User;
import com.grabit.api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveOrUpdateUser(String gitHubId) {
        User user = new User();
        user.setGitHubId(gitHubId);
        user.setJoinedAt(LocalDate.now());
      
        userRepository.save(user);
    }
}

