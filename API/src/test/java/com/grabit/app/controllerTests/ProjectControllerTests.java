package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectController;
import com.grabit.app.dto.*;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProjectController projectController;

    private User user;
    private Project project;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserID(1);

        project = new Project();
        project.setActive(true);
    }

    @Test
    void createProject_Success() {
        ProjectCreationDTO request = new ProjectCreationDTO("Project A", "Description...");
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        ResponseEntity<CreateResponseDTO> response = projectController.createProject(request, authentication);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Your project has been created successfully", response.getBody().getMessage());
    }

    @Test
    void getAllProjects_Success() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.getAllProjects(user, true)).thenReturn(List.of());

        ResponseEntity<List<ProjectAndRoleDTO>> response = projectController.getAllProjects(true, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getProjectByID_ValidCollaborator() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectCollaborator(1, 1)).thenReturn(true);

        ResponseEntity<Project> response = projectController.getProjectByID(1, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getProjectByID_InvalidCollaborator_ThrowsException() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectCollaborator(1, 1)).thenReturn(false);

        assertThrows(BadRequest.class, () -> projectController.getProjectByID(1, authentication));
    }

    @Test
    void closeProject_AsProjectLead() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectLead(user, 1)).thenReturn(true);

        ResponseEntity<Project> response = projectController.closeProject(1, authentication);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void closeProject_NotProjectLead_ThrowsException() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectLead(user, 1)).thenReturn(false);

        assertThrows(BadRequest.class, () -> projectController.closeProject(1, authentication));
    }

    @Test
    void getProjectTasks_Success() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectCollaborator(1, 1)).thenReturn(true);
        when(projectService.getProjectTasksByProjectID(1)).thenReturn(List.of());

        ResponseEntity<List<TaskDTO>> response = projectController.getProjectTasks(1, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getProjectTasks_InvalidCollaborator_ThrowsException() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectCollaborator(1, 1)).thenReturn(false);

        assertThrows(BadRequest.class, () -> projectController.getProjectTasks(1, authentication));
    }

    @Test
    void getProjectCollaborators_Success() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectCollaborator(1, 1)).thenReturn(true);
        when(projectService.getProjectCollaboratorsByProjectID(1)).thenReturn(Arrays.asList(new ProjectCollaborator()));

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateProject_AsProjectLead() {
        when(projectService.getProjectByID(1)).thenReturn(project);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectLead(user, 1)).thenReturn(true);
        when(projectService.updateProject(eq(1), any(Project.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.updateProject(1, project, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateProject_NotProjectLead_ThrowsException() {
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(projectService.isProjectLead(user, 1)).thenReturn(false);

        assertThrows(BadRequest.class, () -> projectController.updateProject(1, project, authentication));
    }
}
