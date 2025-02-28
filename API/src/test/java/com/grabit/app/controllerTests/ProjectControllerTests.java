package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectController;
import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.ProjectService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectCollaboratorService projectCollaboratorService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private Project savedProject;
    private ProjectCollaborator projectCollaborator;
    private ProjectCreationDTO request;
    private List<Task> tasks;
    private List<ProjectCollaborator> projectCollaborators;
    private Object leaderboard;

    @BeforeEach
    public void setUp() {
        project = new Project();
        projectCollaborator = new ProjectCollaborator();
        request = new ProjectCreationDTO(project, projectCollaborator);
        tasks = List.of(new Task());
        projectCollaborators = List.of(projectCollaborator);
        leaderboard = new Object();
        savedProject = mock(Project.class);
    }

    @Test
    public void testCreateProject() {
        when(projectService.createProject(project)).thenReturn(savedProject);

        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("github_access_token")).thenReturn("mocked_github_token");

        ResponseEntity<Project> response = projectController.createProject(request, mockUser, mockSession);

        verify(projectService, times(1)).createProject(project);
        verify(projectCollaboratorService, times(1)).addProjectCollaborator(projectCollaborator);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(savedProject);
    }

    @Test
    public void testGetAllProjects() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        ResponseEntity<List<ProjectAndRoleDTO>> response = projectController.getAllProjects(mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetProjectById() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(projectService.isCollaborator(1, mockSession)).thenReturn(true);

        when(projectService.getProjectByID(1)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectByID(1, mockUser, mockSession);

        verify(projectService, times(1)).isCollaborator(1, mockSession);
        verify(projectService, times(1)).getProjectByID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @Test
    public void testCloseProject() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("github_access_token")).thenReturn("mocked_github_token");
        when(projectService.isProjectLead("mocked_github_token", 5)).thenReturn(true);

        ResponseEntity<Void> response = projectController.closeProject(5, mockUser, mockSession);

        verify(projectService, times(1)).isProjectLead("mocked_github_token", 5);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetProjectTasks() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(projectService.isCollaborator(1, mockSession)).thenReturn(true);
        when(projectService.getProjectTasksByProjectID(1)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, mockUser, mockSession);

        verify(projectService, times(1)).isCollaborator(1, mockSession);
        verify(projectService, times(1)).getProjectTasksByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tasks);
    }

    @Test
    public void testGetProjectCollaborators() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(projectService.isCollaborator(1, mockSession)).thenReturn(true);
        when(projectService.getProjectCollaboratorsByProjectID(1)).thenReturn(projectCollaborators);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, mockUser,
                mockSession);

        verify(projectService, times(1)).isCollaborator(1, mockSession);
        verify(projectService, times(1)).getProjectCollaboratorsByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(projectCollaborators);
    }

    @Test
    public void testGetProjectLeaderboard() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(projectService.isCollaborator(1, mockSession)).thenReturn(true);
        when(projectService.getProjectLeaderboardByProjectID(1)).thenReturn(leaderboard);

        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1, mockUser, mockSession);

        verify(projectService, times(1)).isCollaborator(1, mockSession);
        verify(projectService, times(1)).getProjectLeaderboardByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(leaderboard);
    }

    @Test
    public void testUpdateProject() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("github_access_token")).thenReturn("mocked_github_token");
        when(projectService.isProjectLead("mocked_github_token", 1)).thenReturn(true);
        when(projectService.updateProject(1, project)).thenReturn(savedProject);

        ResponseEntity<Project> response = projectController.updateProject(1, project, mockUser, mockSession);

        verify(projectService, times(1)).isProjectLead("mocked_github_token", 1);
        verify(projectService, times(1)).updateProject(1, project);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedProject);
    }
}
