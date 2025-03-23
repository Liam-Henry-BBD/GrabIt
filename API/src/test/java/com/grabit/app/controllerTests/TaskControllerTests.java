package com.grabit.app.controllerTests;

import com.grabit.app.controller.TaskController;
import com.grabit.app.dto.CreateResponseDTO;
import com.grabit.app.dto.TaskDTO;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.service.TaskService;
import com.grabit.app.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskController taskController;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserID(1);

        task = new Task();
    }

    @Test
    void getTaskByID_Success() {
        TaskDTO taskDTO = new TaskDTO();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(taskService.getTaskById(1, user)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.getTaskByID(1, authentication);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteTask_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        ResponseEntity<Void> response = taskController.deleteTask(1, authentication);

        assertEquals(202, response.getStatusCode().value());
        verify(taskService, times(1)).deleteTask(1, user);
    }

    @Test
    void createTask_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        ResponseEntity<CreateResponseDTO> response = taskController.createTask(task, authentication);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Task created successfully", response.getBody().getMessage());
    }

    @Test
    void updateTask_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(taskService.updateTask(1, task, user)).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTask(1, task, authentication);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getCollaboratorByTaskID_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(taskService.getTaskCollaborators(1, user)).thenReturn(List.of(new TaskCollaborator()));

        ResponseEntity<List<TaskCollaborator>> response = taskController.getCollaboratorByTaskID(1, authentication);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateTaskStatus_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(taskService.updateTaskStatus(1, (byte) 2, user)).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTaskStatus(1, (byte) 2, authentication);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void grabTask_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(taskService.grabTask(1, user)).thenReturn(task);

        ResponseEntity<Task> response = taskController.grabTask(1, authentication);

        assertEquals(202, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
