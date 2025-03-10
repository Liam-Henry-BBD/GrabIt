package com.grabit.app.serviceTests;

import com.grabit.app.model.User;
import com.grabit.app.service.UserService;
import com.grabit.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrUpdateUser_userNotFound_savesNewUser() {
        String gitHubID = "testGitHubID";
        when(userRepository.findByGitHubID(gitHubID)).thenReturn(null);

        userService.saveOrUpdateUser(gitHubID);

        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void saveOrUpdateUser_userExists_doesNotSaveUser() {
        String gitHubID = "existingGitHubID";
        User existingUser = new User();
        existingUser.setGitHubID(gitHubID);
        when(userRepository.findByGitHubID(gitHubID)).thenReturn(existingUser);

        userService.saveOrUpdateUser(gitHubID);

        verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

}
