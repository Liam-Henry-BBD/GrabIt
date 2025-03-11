package com.grabit.app.controllerTests;

import com.grabit.app.controller.TaskController;
import com.grabit.app.dto.CreateResponseDTO;
import com.grabit.app.dto.TaskDTO;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.service.UserService;
import com.grabit.app.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;
    private Authentication authentication;
    private Task task;
    private TaskCollaborator collaborator;

    @BeforeEach
    public void setUp() {
        task = new Task();
        collaborator = new TaskCollaborator();
        List.of(collaborator);
        authentication = mock(Authentication.class);
    }

    @Test
    public void testGetTaskById() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<TaskDTO> response = taskController.getTaskByID(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).getTaskById(1, testUser);
    }

    @Test
    public void testDeleteTask() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<Void> response = taskController.deleteTask(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskService, times(1)).deleteTask(1, testUser);
    }

    @Test
    public void testCreateTask() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<CreateResponseDTO> response = taskController.createTask(task, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).createTask(task, testUser);
    }

    @Test
    public void testUpdateTask() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<Task> response = taskController.updateTask(1, task, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskService, times(1)).updateTask(1, task, testUser);
    }

    @Test
    public void testGetCollaboratorByTaskID() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<List<TaskCollaborator>> response = taskController.getCollaboratorByTaskID(1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).getTaskCollaborators(1, testUser);
    }

    @Test
    public void testUpdateTaskStatus() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);

        ResponseEntity<Task> response = taskController.updateTaskStatus(1, (byte) 1, authentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskService, times(1)).updateTaskStatus(1, (byte) 1, testUser);
    }
}
