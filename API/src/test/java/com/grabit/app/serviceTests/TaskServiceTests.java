package com.grabit.app.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import com.grabit.app.model.User;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskStatus;
import com.grabit.app.model.TaskPoint;
import com.grabit.app.model.Project;
import com.grabit.app.repository.TaskRepository;
import com.grabit.app.repository.TaskStatusRepository;
import com.grabit.app.repository.TaskPointRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.grabit.app.enums.Roles;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.enums.Status;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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

    private User mockUser;
    private Task mockTask;
    private TaskStatus mockStatus;
    private TaskPoint mockTaskPoint;
    private Project mockProject;

    @BeforeEach
    void setUp() {
        mockUser = new User(); 
        mockTask = new Task(); 
        mockStatus = new TaskStatus(); 
        mockTaskPoint = new TaskPoint(); 
        mockProject = new Project(); 
    }

//    @Test
//    void testGetTaskById_CollaboratorAllowed() {
//        int taskID = 1;
//        mockTask.setTaskID(taskID);
//        mockUser.setUserID(1);
//        when(taskRepository.existsTaskByUserIDAndTaskID(taskID, mockUser.getUserID())).thenReturn(true);
//        when(taskRepository.findById(taskID)).thenReturn(Optional.of(mockTask));
//
//        Task result = taskService.getTaskById(taskID, mockUser);
//
//        assertNotNull(result);
//        assertEquals(taskID, result.getTaskID());
//    }

    @Test
    void testGetTaskById_NotCollaborator_ShouldThrowBadRequest() {
        int taskID = 1;
        mockUser.setUserID(1);
        when(taskRepository.existsTaskByUserIDAndTaskID(taskID, mockUser.getUserID())).thenReturn(false);

        assertThrows(BadRequest.class, () -> taskService.getTaskById(taskID, mockUser));
    }

//    @Test
//    void testCreateTask_ProjectLeadAllowed() {
//        mockUser.setUserID(1);
//        mockTask.setProject(mockProject);
//        mockTask.setTaskPoint(mockTaskPoint);
//        mockTask.setTaskStatus(mockStatus);
//
//        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(mockUser.getUserID(),
//                mockTask.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole())).thenReturn(true);
//        when(taskPointRepository.existsById((int) mockTask.getTaskPoint().getTaskPointID())).thenReturn(true);
//        when(taskStatusRepository.existsById((int) mockTask.getTaskStatus().getTaskStatusID())).thenReturn(true);
//        when(taskRepository.save(mockTask)).thenReturn(mockTask);
//
//        Task result = taskService.createTask(mockTask, mockUser);
//
//        assertNotNull(result);
//    }

    @Test
    void testCreateTask_NotProjectLead_ShouldThrowBadRequest() {
        mockUser.setUserID(1);
        mockTask.setProject(mockProject);

        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(mockUser.getUserID(), 
                mockTask.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole())).thenReturn(false);

        assertThrows(BadRequest.class, () -> taskService.createTask(mockTask, mockUser));
    }


    @Test
    void testUpdateTaskStatus_NotCollaborator_ShouldThrowBadRequest() {
        int taskID = 1;
        byte newStatusID = Status.GRABBED.getStatus();

        when(taskCollaboratorRepository.existsByTaskIDAndUserID(taskID, mockUser.getUserID())).thenReturn(false);

        assertThrows(BadRequest.class, () -> taskService.updateTaskStatus(taskID, newStatusID, mockUser));
    }



}
