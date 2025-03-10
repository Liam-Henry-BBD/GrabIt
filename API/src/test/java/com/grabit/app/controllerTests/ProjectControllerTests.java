package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectController;
import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.dto.ProjectLeaderboardDTO;
import com.grabit.app.model.*;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectController projectController;

    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authentication = mock(Authentication.class);
    }

    @Test
    public void testCreateProject() {
        ProjectCreationDTO request = new ProjectCreationDTO("Project A", "Description");
        User testUser = new User();
        Project newProject = new Project();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.createProject(any(ProjectCreationDTO.class), eq(testUser))).thenReturn(newProject);

        ResponseEntity<Project> response = projectController.createProject(request, authentication);

        verify(projectService, times(1)).createProject(any(ProjectCreationDTO.class), eq(testUser));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(newProject);
    }

    @Test
    public void testGetAllProjects() {
        User testUser = new User();

        List<ProjectAndRoleDTO> projects = List.of(new ProjectAndRoleDTO(1, "project", "blah blah", 2, (byte) 1));

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.getAllProjects(testUser)).thenReturn(projects);

        ResponseEntity<List<ProjectAndRoleDTO>> response = projectController.getAllProjects(authentication);

        verify(projectService, times(1)).getAllProjects(testUser);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(projects);
    }


    @Test
    public void testGetProjectByID() {
        User testUser = new User();
        testUser.setUserID(1);
        Project project = new Project();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(true);
        when(projectService.getProjectByID(1)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectByID(1, authentication);

        verify(projectService, times(1)).isProjectCollaborator(eq(testUser.getUserID()), eq(1));
        verify(projectService, times(1)).getProjectByID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @Test
    public void testGetProjectByID_FORBIDDEN() {
        User testUser = new User();
        testUser.setUserID(1);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(false);

        ResponseEntity<Project> response = projectController.getProjectByID(1, authentication);

        verify(projectService, times(0)).getProjectByID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testCloseProject() {
        User testUser = new User();
        Project project = new Project();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), eq(1))).thenReturn(true);
        when(projectService.getProjectByID(1)).thenReturn(project);

        ResponseEntity<Project> response = projectController.closeProject(1, authentication);

        verify(projectService, times(1)).closeProject(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    public void testCloseProject_FORBIDDEN() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), eq(2))).thenReturn(false);

        ResponseEntity<Project> response = projectController.closeProject(1, authentication);

        verify(projectService, times(0)).closeProject(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectTasks() {
        User testUser = new User();
        List<Task> tasks = List.of(new Task());

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(true);
        when(projectService.getProjectTasksByProjectID(1)).thenReturn(tasks);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, authentication);

        verify(projectService, times(1)).getProjectTasksByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tasks);
    }

    @Test
    public void testGetProjectTasks_FORBIDDEN() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(false);

        ResponseEntity<List<Task>> response = projectController.getProjectTasks(1, authentication);

        verify(projectService, times(0)).getProjectTasksByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectCollaborators() {
        User testUser = new User();
        List<ProjectCollaborator> collaborators = List.of(new ProjectCollaborator());

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(true);
        when(projectService.getProjectCollaboratorsByProjectID(1)).thenReturn(collaborators);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, authentication);

        verify(projectService, times(1)).getProjectCollaboratorsByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(collaborators);
    }

    @Test
    public void testGetProjectCollaborators_FORBIDDEN() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(false);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1, authentication);

        verify(projectService, times(0)).getProjectCollaboratorsByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetProjectLeaderboard() {
        User testUser = new User();
//        List<ProjectLeaderboardDTO> leaderboard = new List<>();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(true);
//        when(projectService.getProjectLeaderboardByProjectID(1)).thenReturn( leaderboard);

        ResponseEntity<List<ProjectLeaderboardDTO>> response = projectController.getProjectLeaderboard(1, authentication);

        verify(projectService, times(1)).getProjectLeaderboardByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(leaderboard);
    }

    @Test
    public void testGetProjectLeaderboard_FORBIDDEN() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectCollaborator(eq(testUser.getUserID()), eq(1))).thenReturn(false);

        ResponseEntity<List<ProjectLeaderboardDTO>> response = projectController.getProjectLeaderboard(1, authentication);

        verify(projectService, times(0)).getProjectLeaderboardByProjectID(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void testUpdateProject() {
        User testUser = new User();
        Project updatedProject = new Project();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), eq(1))).thenReturn(true);
        when(projectService.updateProject(eq(1), any(Project.class))).thenReturn(updatedProject);

        ResponseEntity<Project> response = projectController.updateProject(1, updatedProject, authentication);

        verify(projectService, times(1)).updateProject(eq(1), any(Project.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedProject);
    }

    @Test
    public void testUpdateProject_FORBIDDEN() {
        User testUser = new User();

        when(userService.getAuthenticatedUser(authentication)).thenReturn(testUser);
        when(projectService.isProjectLead(eq(testUser), eq(1))).thenReturn(false);

        ResponseEntity<Project> response = projectController.updateProject(1, new Project(), authentication);

        verify(projectService, times(0)).updateProject(eq(1), any(Project.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
