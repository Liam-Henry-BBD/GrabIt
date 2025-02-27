package com.grabit.app.controllerTests;

import com.grabit.app.controller.TaskController;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;
    private TaskCollaborator collaborator;
    private List<TaskCollaborator> collaborators;

    @BeforeEach
    public void setUp() {
        task = new Task();
        collaborator = new TaskCollaborator();
        collaborators = List.of(collaborator);
    }

    @Test
    public void testGetTaskById() {
        ResponseEntity<Task> response = taskController.getTaskById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).getTaskById(1);
    }

    @Test
    public void testDeleteTask() {
        ResponseEntity<Void> response = taskController.deleteTask(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(taskService, times(1)).deleteTask(1);
    }

    @Test
    public void testCreateTask() {
        ResponseEntity<Task> response = taskController.createTask(task);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).createTask(task);
    }

    @Test
    public void testUpdateTask() {
        ResponseEntity<Task> response = taskController.updateTask(1, task);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskService, times(1)).updateTask(1, task);
    }

    @Test
    public void testGetCollaboratorByTaskID() {
        ResponseEntity<List<TaskCollaborator>> response = taskController.getCollaboratorByTaskID(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService, times(1)).getTaskCollaborators(1);
    }

    @Test
    public void testUpdateTaskStatus() {
        ResponseEntity<Task> response = taskController.updateTaskStatus(1, (byte) 1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(taskService, times(1)).updateTaskStatus(1, (byte) 1);
    }
}
