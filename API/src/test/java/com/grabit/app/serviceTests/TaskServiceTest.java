package com.grabit.app.serviceTests;

import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.TaskStatus;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.TaskPointRepository;
import com.grabit.app.repository.TaskRepository;
import com.grabit.app.repository.TaskStatusRepository;
import com.grabit.app.service.TaskService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @Mock
    private TaskPointRepository taskPointRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskCollaboratorRepository taskCollaboratorRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasksByProjectID() {
        Integer projectID = 1;
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByProjectID(projectID)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByProjectID(projectID);

        assertEquals(tasks, result);
        verify(taskRepository).findByProjectID(projectID);
    }

    @Test
    public void testGetTaskById() {
        Integer taskID = 1;
        Task task = new Task();
        when(taskRepository.findById(taskID)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskID);

        assertEquals(task, result);
        verify(taskRepository).findById(taskID);
    }

    @Test
    public void testGetTaskById_NotFound() {
        Integer taskID = 1;
        when(taskRepository.findById(taskID)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskService.getTaskById(taskID));
        verify(taskRepository).findById(taskID);
    }

    @Test
    public void testUpdateTaskStatus() {
        int taskID = 1;
        byte taskStatusID = 2;
        Task task = new Task();
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTaskStatusID(taskStatusID);
        task.setTaskStatus(new TaskStatus());
        task.getTaskStatus().setStatusName("Available");

        when(taskRepository.findById(taskID)).thenReturn(Optional.of(task));
        when(taskStatusRepository.findById((int) taskStatusID)).thenReturn(Optional.of(taskStatus));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTaskStatus(taskID, taskStatusID);

        assertEquals(taskStatus, result.getTaskStatus());
        verify(taskRepository).findById(taskID);
        verify(taskStatusRepository).findById((int) taskStatusID);
        verify(taskRepository).save(task);
    }

    @Test
    public void testUpdateTask() {
        int taskID = 1;
        Task task = new Task();
        task.setTaskName("New Task");
        task.setTaskDescription("New Description");

        Task existingTask = new Task();
        existingTask.setTaskStatus(new TaskStatus());
        existingTask.getTaskStatus().setStatusName("In Progress");

        when(taskRepository.findById(taskID)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(taskID, task);

        assertEquals("New Task", result.getTaskName());
        assertEquals("New Description", result.getTaskDescription());
        verify(taskRepository).findById(taskID);
        verify(taskRepository).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        int taskID = 1;
        Task task = new Task();

        when(taskRepository.findById(taskID)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskID);

        verify(taskRepository).findById(taskID);
        verify(taskRepository).delete(task);
    }

    @Test
    public void testGetTaskCollaborators() {
        int taskID = 1;
        List<TaskCollaborator> collaborators = Arrays.asList(new TaskCollaborator(), new TaskCollaborator());

        when(taskRepository.existsById(taskID)).thenReturn(true);
        when(taskCollaboratorRepository.findByTaskID(taskID)).thenReturn(collaborators);

        List<TaskCollaborator> result = taskService.getTaskCollaborators(taskID);

        assertEquals(collaborators, result);
        verify(taskRepository).existsById(taskID);
        verify(taskCollaboratorRepository).findByTaskID(taskID);
    }

    @Test
    public void testFilterTaskByTaskStatus() {
        int taskStatusID = 1;
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskRepository.findByTaskStatusID(taskStatusID)).thenReturn(tasks);

        List<Task> result = taskService.filterTaskByTaskStatus(taskStatusID);

        assertEquals(tasks, result);
        verify(taskRepository).findByTaskStatusID(taskStatusID);
    }
}