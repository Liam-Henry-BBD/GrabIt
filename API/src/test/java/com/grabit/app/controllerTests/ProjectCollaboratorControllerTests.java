package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectCollaboratorController;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class ProjectCollaboratorControllerTests {

    @Mock
    private ProjectCollaboratorService projectCollaboratorService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProjectCollaboratorController projectCollaboratorController;

    private ProjectCollaborator projectCollaborator;
    private User user;

    @BeforeEach
    public void setUp() {
        projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProjectID(1);

        user = new User();
    }

    @Test
    public void testGetProjectCollaboratorByID() {
        ResponseEntity<ProjectCollaborator> response = projectCollaboratorController.getProjectCollaboratorByID(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(projectCollaboratorService, times(1)).getProjectCollaboratorByID(1);
    }

    @Test
    public void testDeactivateProjectCollaborator() {
        ResponseEntity<Void> response = projectCollaboratorController.deactivateProjectCollaborator(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(projectCollaboratorService, times(1)).deactivateProjectCollaborator(1);
    }

    @Test
    public void testCreateProjectCollaborator() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        ResponseEntity<Void> response = projectCollaboratorController.createProjectCollaborator(projectCollaborator, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(projectCollaboratorService, times(1)).addProjectCollaborator(projectCollaborator, user);
    }
}
