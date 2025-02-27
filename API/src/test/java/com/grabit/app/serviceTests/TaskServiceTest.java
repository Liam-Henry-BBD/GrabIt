package com.grabit.app.serviceTests;

import org.mockito.InjectMocks;
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
        Integer taskId = 1;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        assertEquals(task, result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    public void testGetTaskById_NotFound() {
        Integer taskId = 1;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository).findById(taskId);
    }

    @Test
    public void testUpdateTaskStatus() {
        int taskId = 1;
        byte taskStatusId = 2;
        Task task = new Task();
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTaskStatusID(taskStatusId);
        task.setTaskStatus(new TaskStatus());
        task.getTaskStatus().setStatusName("Available");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskStatusRepository.findById((int) taskStatusId)).thenReturn(Optional.of(taskStatus));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTaskStatus(taskId, taskStatusId);

        assertEquals(taskStatus, result.getTaskStatus());
        verify(taskRepository).findById(taskId);
        verify(taskStatusRepository).findById((int) taskStatusId);
        verify(taskRepository).save(task);
    }

    @Test
    public void testUpdateTask() {
        int taskId = 1;
        Task task = new Task();
        task.setTaskName("New Task");
        task.setTaskDescription("New Description");

        Task existingTask = new Task();
        existingTask.setTaskStatus(new TaskStatus());
        existingTask.getTaskStatus().setStatusName("In Progress");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(taskId, task);

        assertEquals("New Task", result.getTaskName());
        assertEquals("New Description", result.getTaskDescription());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        int taskId = 1;
        Task task = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(task);
    }

    @Test
    public void testGetTaskCollaborators() {
        int taskId = 1;
        List<TaskCollaborator> collaborators = Arrays.asList(new TaskCollaborator(), new TaskCollaborator());

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskCollaboratorRepository.findByTaskID(taskId)).thenReturn(collaborators);

        List<TaskCollaborator> result = taskService.getTaskCollaborators(taskId);

        assertEquals(collaborators, result);
        verify(taskRepository).existsById(taskId);
        verify(taskCollaboratorRepository).findByTaskID(taskId);
    }

    @Test
    public void testFilterTaskByTaskStatus() {
        int taskStatusId = 1;
        List<Task> tasks = Arrays.asList(new Task(), new Task());

        when(taskRepository.findByTaskStatusID(taskStatusId)).thenReturn(tasks);

        List<Task> result = taskService.filterTaskByTaskStatus(taskStatusId);

        assertEquals(tasks, result);
        verify(taskRepository).findByTaskStatusID(taskStatusId);
    }
}