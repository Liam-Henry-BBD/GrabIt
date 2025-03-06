package com.grabit.app.controllerTests;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;
import com.grabit.app.controller.ProjectController;
import com.grabit.app.model.User;

public class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectCollaboratorService projectCollaboratorService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private ProjectCreationDTO request;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        request = new ProjectCreationDTO("Project B", "Project B Description");
        authentication = mock(Authentication.class);
    }

    @Test
    public void testCreateProject() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.createProject(any(ProjectCreationDTO.class), eq(testUser))).thenReturn(project);

        ResponseEntity<Project> response = projectController.createProject(request, authentication);

        verify(projectService, times(1)).createProject(any(ProjectCreationDTO.class), eq(testUser));
        verify(projectCollaboratorService, times(1)).addProjectCollaborator(any(ProjectCollaborator.class), eq(testUser));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @Test
    public void testGetProjectByID() {
        User testUser = new User();
        testUser.setUserID(1);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(true);
        when(projectService.getProjectByID(anyInt())).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectByID(1, authentication);

        verify(projectService, times(1)).isProjectCollaborator(anyInt(), eq(testUser.getUserID()));
        verify(projectService, times(1)).getProjectByID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @Test
    public void testGetProjectByID_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(1);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(false);

        ResponseEntity<Project> response = projectController.getProjectByID(1, authentication);

        verify(projectService, times(0)).getProjectByID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testCloseProject() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), anyInt())).thenReturn(true);

        ResponseEntity<Void> response = projectController.closeProject(1, authentication);

        verify(projectService, times(1)).isProjectLead(eq(testUser), anyInt());
        verify(projectService, times(1)).closeProject(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    public void testCloseProject_FORBIDDEN() {
        User testUser = new User();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), anyInt())).thenReturn(false);

        ResponseEntity<Void> response = projectController.closeProject(1, authentication);

        verify(projectService, times(1)).isProjectLead(eq(testUser), anyInt());
        verify(projectService, times(0)).closeProject(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectTasks() {
        List<Task> tasks = List.of(new Task());
        User testUser = new User();
        testUser.setUserID(2);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(true);
        when(projectService.getProjectTasksByProjectID(anyInt())).thenReturn(tasks);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, authentication);

        verify(projectService, times(1)).getProjectTasksByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tasks);
    }

    @Test
    public void testGetProjectTasks_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(2);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(false);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, authentication);

        verify(projectService, times(0)).getProjectTasksByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectCollaborators() {
        User testUser = new User();
        testUser.setUserID(6);

        List<ProjectCollaborator> collaborators = List.of(new ProjectCollaborator());

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(true);
        when(projectService.getProjectCollaboratorsByProjectID(anyInt())).thenReturn(collaborators);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, authentication);

        verify(projectService, times(1)).getProjectCollaboratorsByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(collaborators);
    }

    @Test
    public void testGetProjectCollaborators_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(6);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(false);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, authentication);

        verify(projectService, times(0)).getProjectCollaboratorsByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectLeaderboard() {
        User testUser = new User();
        testUser.setUserID(3);

        Object leaderboard = new Object();
        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(true);
        when(projectService.getProjectLeaderboardByProjectID(anyInt())).thenReturn(leaderboard);

        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1, authentication);

        verify(projectService, times(1)).isProjectCollaborator(anyInt(), eq(testUser.getUserID()));
        verify(projectService, times(1)).getProjectLeaderboardByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(leaderboard);
    }

    @Test
    public void testGetProjectLeaderboard_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(3);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(anyInt(), eq(testUser.getUserID()))).thenReturn(false);

        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1, authentication);

        verify(projectService, times(1)).isProjectCollaborator(anyInt(), eq(testUser.getUserID()));
        verify(projectService, times(0)).getProjectLeaderboardByProjectID(anyInt());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testUpdateProject() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), anyInt())).thenReturn(true);
        when(projectService.updateProject(anyInt(), any(Project.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.updateProject(1, project, authentication);

        when(projectService.isProjectLead(eq(testUser), anyInt())).thenReturn(true);
        verify(projectService, times(1)).updateProject(anyInt(), any(Project.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @Test
    public void testUpdateProject_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(4);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), anyInt())).thenReturn(false);

        ResponseEntity<Project> response = projectController.updateProject(1, project, authentication);

        verify(projectService, times(0)).updateProject(anyInt(), any(Project.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
