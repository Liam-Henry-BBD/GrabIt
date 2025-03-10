package com.grabit.app.serviceTests;

import com.grabit.app.enums.Roles;
import com.grabit.app.service.TaskService;

import com.grabit.app.enums.Status;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.model.*;
import com.grabit.app.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;
    
    @Mock
    private TaskStatusRepository taskStatusRepository;
    
    @Mock
    private TaskPointRepository taskPointRepository;
    
    @Mock
    private TaskCollaboratorRepository taskCollaboratorRepository;
    
    @Mock
    private ProjectCollaboratorRepository projectCollaboratorRepository;
    
    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;
    private TaskStatus testStatus;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setUserID(1);
        testTask = new Task();
        testTask.setTaskID(1);
        testTask.setProject(new Project());
        testTask.setTaskStatus(new TaskStatus());
        testStatus = new TaskStatus();
    }

    @Test
    public void testGetTaskById_Success() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        when(taskRepository.existsTaskByUserIDAndTaskID(1, 1)).thenReturn(true);
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(1, 1, Roles.PROJECT_LEAD.getRole())).thenReturn(false);
        
        Task task = taskService.getTaskById(1, testUser);
        
        assertThat(task).isEqualTo(testTask);
    }

    @Test
    public void testGetTaskById_Fail_NotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> taskService.getTaskById(1, testUser))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    public void testCreateTask_Fail_NotProjectLead() {
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(1, 1, Roles.PROJECT_LEAD.getRole())).thenReturn(false);
        
        assertThatThrownBy(() -> taskService.createTask(testTask, testUser))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("User is not a lead of this project.");
    }

    @Test
    public void testUpdateTaskStatus_Fail_TaskNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> taskService.updateTaskStatus(1, Status.GRABBED.getStatus(), testUser))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    public void testUpdateTask_Fail_TaskAlreadyCompleted() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        when(taskStatusRepository.findById((int)Status.COMPLETE.getStatus())).thenReturn(Optional.of(testStatus));
        
        assertThatThrownBy(() -> taskService.updateTask(1, testTask, testUser))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("400 BAD_REQUEST \"Cannot update task. Not a project lead.");
    }

    @Test
    public void testDeleteTask_Fail_NotProjectLead() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(1, 1, Roles.PROJECT_LEAD.getRole())).thenReturn(false);
        
        assertThatThrownBy(() -> taskService.deleteTask(1, testUser))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("Cannot delete task. Not a project lead.");
    }

    @Test
    public void testGrabTask_Fail_NotAvailable() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        testTask.setTaskStatus(new TaskStatus(Status.GRABBED.getStatus(), "Grabbed"));
        
        assertThatThrownBy(() -> taskService.grabTask(1, 1, testUser))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("Task is not available to be grabbed.");
    }

    @Test
    public void testGrabTask_Fail_NotProjectCollaborator() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        when(taskStatusRepository.findById((int)Status.AVAILABLE.getStatus())).thenReturn(Optional.of(testStatus));
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(1, 1, Roles.PROJECT_MEMBER.getRole())).thenReturn(false);
        
        assertThatThrownBy(() -> taskService.grabTask(1, 1, testUser))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("Task is not available to be grabbed.");
    }
}
