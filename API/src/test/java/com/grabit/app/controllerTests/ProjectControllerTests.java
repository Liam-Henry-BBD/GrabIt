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
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectCollaboratorService projectCollaboratorService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private ProjectCollaborator projectCollaborator;
    private ProjectCreationDTO request;
    private Task task;
    private List<Project> projects;
    private List<ProjectCollaborator> projectCollaborators;
    private List<Task> tasks;
    private Object leaderboard;

    @BeforeEach
    public void setUp() {
        project = new Project();
        projectCollaborator = new ProjectCollaborator();
        task = new Task();
        request = new ProjectCreationDTO(project, projectCollaborator);
        tasks = List.of(task);
        projects = List.of(project);
        projectCollaborators = List.of(projectCollaborator);
    }

//    @Test
//    public void testCreateProject() {
//        projectService.createProject(project);
//        projectCollaboratorService.addProjectCollaborator(projectCollaborator);
//        ResponseEntity<Project> response = projectController.createProject(request);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        verify(projectService, times(1)).createProject(project);
////        verify(projectCollaboratorService, times(1)).addProjectCollaborator(projectCollaborator);
//    }

    @Test
    public void testGetAllProjects() {
        projectService.getAllProjects();
        ResponseEntity<List<Project>> response = projectController.getAllProjects();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    public void testGetProjectById() {
        projectService.getProjectById(1);
        ResponseEntity<Project> response = projectController.getProjectById(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(projectService, times(1)).getProjectById(1);
    }

    @Test
    public void testCloseProject() {
        projectService.closeProject(1);
        ResponseEntity<Void> response = projectController.closeProject(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        verify(projectService, times(1)).closeProject(1);
    }

//    @Test
//    public void testGetProjectTasks() {
//        projectService.getProjectTasksByProjectId(1);
//        List<Task> response = projectController.getProjectTasks(1);
//        assertThat(response).isEqualTo(tasks);
////        verify(projectService, times(1)).getProjectTasksByProjectId(1);
//    }

    @Test
    public void testGetProjectCollaborators() {
        projectService.getProjectCollaboratorsByProjectId(1);
        ResponseEntity<List<ProjectCollaborator>> response = projectController.getProjectCollaborators(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(projectService, times(1)).getProjectCollaboratorsByProjectId(1);
    }

    @Test
    public void testGetProjectLeaderboard() {
        projectService.getProjectLeaderboardByProjectId(1);
        ResponseEntity<Object> response = projectController.getProjectLeaderboard(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(projectService, times(1)).getProjectLeaderboardByProjectId(1);
    }

    @Test
    public void testUpdateProject() {
        projectService.updateProject(1, project);
        ResponseEntity<Project> response = projectController.updateProject(1, project);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        verify(projectService, times(1)).updateProject(1, project);
    }
}
