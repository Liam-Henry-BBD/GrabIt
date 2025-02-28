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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

    @BeforeEach
    public void setUp() {
        project = new Project();
        projectCollaborator = new ProjectCollaborator();
        request = new ProjectCreationDTO(project, projectCollaborator);
        tasks = List.of(new Task());
    }

    @Test
    public void testCreateProject() {
        savedProject = new Project();
        savedProject = mock(Project.class);

        when(savedProject.getProjectID()).thenReturn(1);
        when(projectService.createProject(project)).thenReturn(savedProject);
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute(anyString())).thenReturn(true);

        ResponseEntity<Project> response = projectController.createProject(request, mockUser, mockSession);

        verify(projectService, times(1)).createProject(project);
        verify(projectCollaboratorService, times(1)).addProjectCollaborator(projectCollaborator);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
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
        when(projectService.isProjectCollaborator(anyString(), eq(1))).thenReturn(true);

        ResponseEntity<Project> response = projectController.getProjectByID(1, mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testCloseProject() {
        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(projectService.isProjectLead(anyString(), eq(1))).thenReturn(true);

        ResponseEntity<Void> response = projectController.closeProject(1, mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetProjectTasks() {
        when(projectService.getProjectTasksByProjectID(1)).thenReturn(tasks);
        when(projectService.isProjectCollaborator(anyString(), eq(1))).thenReturn(true);

        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectCollaborators() {
        List<ProjectCollaborator> projectCollaborators = List.of(projectCollaborator);
        when(projectService.getProjectCollaboratorsByProjectID(1)).thenReturn(projectCollaborators);
        when(projectService.isProjectCollaborator(anyString(), eq(1))).thenReturn(true);

        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, mockUser,
                mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(null);
    }

    @Test
    public void testGetProjectLeaderboard() {
        Object leaderboard = new Object();
        when(projectService.getProjectLeaderboardByProjectID(1)).thenReturn(leaderboard);
        when(projectService.isProjectCollaborator(anyString(), eq(1))).thenReturn(true);

        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1, mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(null);
    }

    @Test
    public void testUpdateProject() {
        when(projectService.isProjectLead(anyString(), eq(1))).thenReturn(true);

        OAuth2User mockUser = mock(OAuth2User.class);
        HttpSession mockSession = mock(HttpSession.class);
        ResponseEntity<Project> response = projectController.updateProject(1, project, mockUser, mockSession);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedProject);
    }
}
