package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectController;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.ProjectService;

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

        ResponseEntity<Project> response = projectController.createProject(request);

        verify(projectService, times(1)).createProject(project);
        verify(projectCollaboratorService, times(1)).addProjectCollaborator(projectCollaborator);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testGetAllProjects() {
        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(projectService, times(1)).getAllProjects();
    }

    // the getProjectById method takes in more parameters now, this spec needs to be adjusted.

    // @Test
    // public void testGetProjectById() {
    // ResponseEntity<Project> response = projectController.getProjectByID(1);

    // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // verify(projectService, times(1)).getProjectByID(1);
    // }

    @Test
    public void testCloseProject() {
        ResponseEntity<Void> response = projectController.closeProject(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(projectService, times(1)).closeProject(1);
    }

    @Test
    public void testGetProjectTasks() {
        when(projectService.getProjectTasksByProjectID(1)).thenReturn(tasks);

        List<Task> response = projectController.getProjectTasks(1);

        assertThat(response).isEqualTo(tasks);
        verify(projectService, times(1)).getProjectTasksByProjectID(1);
    }

    @Test
    public void testGetProjectCollaborators() {
        List<ProjectCollaborator> projectCollaborators = List.of(projectCollaborator);
        when(projectService.getProjectCollaboratorsByProjectID(1)).thenReturn(projectCollaborators);

        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(projectCollaborators);
        verify(projectService, times(1)).getProjectCollaboratorsByProjectID(1);
    }

    @Test
    public void testGetProjectLeaderboard() {
        Object leaderboard = new Object();
        when(projectService.getProjectLeaderboardByProjectID(1)).thenReturn(leaderboard);

        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(leaderboard);
        verify(projectService, times(1)).getProjectLeaderboardByProjectID(1);
    }

    @Test
    public void testUpdateProject() {
        ResponseEntity<Project> response = projectController.updateProject(1, project);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedProject);
        verify(projectService, times(1)).updateProject(1, project);
    }
}
