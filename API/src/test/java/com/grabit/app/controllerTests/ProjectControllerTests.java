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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectControllerTests {
    @InjectMocks
    private ProjectController pc;

    @Mock
    private ProjectService ps;

    @Mock
    private ProjectCollaboratorService pcs;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateProject () {
    Project project = new Project();
    ProjectCollaborator collab = new ProjectCollaborator();
    ProjectCreationDTO pcDTO = new ProjectCreationDTO(project, collab);

    Project savedProject = new Project(15, "New Robotics Project", "Something robotics.", new Date(), new Date());

        when(ps.createProject(any(Project.class))).thenReturn(savedProject);

        ResponseEntity<Project> response = pc.createProject(pcDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedProject, response.getBody());

        ArgumentCaptor<ProjectCollaborator> collaboratorCaptor = ArgumentCaptor.forClass(ProjectCollaborator.class);
        verify(pcs).addProjectCollaborator(collaboratorCaptor.capture());
        ProjectCollaborator capturedCollaborator = collaboratorCaptor.getValue();

        assertEquals(15, capturedCollaborator.getProjectID());
        assertEquals(1, capturedCollaborator.getRoleID());
        assertEquals(true, capturedCollaborator.isActive());
        assertEquals(LocalDateTime.now().getDayOfYear(), capturedCollaborator.getJoinedAt().getDayOfYear());
    }

    @Test
    public void testGetAllProjects () {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(ps.getAllProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> response = pc.getAllProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    public void testGetProjectById () {
        Project project = new Project();
        when(ps.getProjectById(3)).thenReturn(project);

        ResponseEntity<Project> response = pc.getProjectById(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    public void testCloseProject () {
        ResponseEntity<Void> response = pc.closeProject(2);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ps).closeProject(2);
    }

    @Test
    public void testGetProjectTasks () {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(ps.getProjectTasksByProjectId(4)).thenReturn(tasks);

        List<Task> response = pc.getProjectTasks(4);

        assertEquals(tasks, response);
    }

    @Test
    public void testGetProjectCollaborators () {
        List<ProjectCollaborator> collaborators = Arrays.asList(new ProjectCollaborator(), new ProjectCollaborator());
        when(ps.getProjectCollaboratorsByProjectId(5)).thenReturn(collaborators);

        ResponseEntity<List<ProjectCollaborator>> response = pc.getProjectCollaborators(5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collaborators, response.getBody());
    }

    @Test
    public void testGetProjectLeaderboard () {
        Object leaderboard = new Object();
        when(ps.getProjectLeaderboardByProjectId(7)).thenReturn(leaderboard);

        ResponseEntity<Object> response = pc.getProjectLeaderboard(7);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(leaderboard, response.getBody());
    }

    @Test
    public void testUpdateProject () {
        Project updatedProject = new Project();
        when(ps.updateProject(10, updatedProject)).thenReturn(updatedProject);

        ResponseEntity<Project> response = pc.updateProject(10, updatedProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProject, response.getBody());
    }
}