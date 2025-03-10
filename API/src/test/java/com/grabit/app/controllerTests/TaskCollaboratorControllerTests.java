package com.grabit.app.controllerTests;

import com.grabit.app.controller.TaskCollaboratorController;
import com.grabit.app.dto.CreateResponseDTO;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.service.TaskCollaboratorService;
import com.grabit.app.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskCollaboratorControllerTests {

    @Mock
    private TaskCollaboratorService taskCollaboratorService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskCollaboratorController taskCollaboratorController;

    private TaskCollaborator taskCollaborator;
    private User user;

    @BeforeEach
    public void setUp() {
        taskCollaborator = new TaskCollaborator();
        user = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
    }

    @Test
    public void testCreateTaskCollaborator() {
        ResponseEntity<CreateResponseDTO> response = taskCollaboratorController.createTaskCollaborator(taskCollaborator, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(taskCollaboratorService).addTaskCollaborator(taskCollaborator, user);
    }

    @Test
    public void testGetTaskCollaboratorByID() {
        ResponseEntity<TaskCollaborator> response = taskCollaboratorController.getTaskCollaboratorByID(55, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskCollaboratorService).getTaskCollaboratorByID(55, user);
    }

    @Test
    public void testDeactivateTaskCollaboratorByID() {
        ResponseEntity<TaskCollaborator> response = taskCollaboratorController.deactivateTaskCollaboratorByID(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskCollaboratorService).deactivateTaskCollaboratorByID(1, user);
    }

    @Test
    public void testActivateTaskCollaboratorByID() {
        ResponseEntity<TaskCollaborator> response = taskCollaboratorController.activateTaskCollaboratorByID(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskCollaboratorService).activateTaskCollaboratorByID(1, user);
    }
}
