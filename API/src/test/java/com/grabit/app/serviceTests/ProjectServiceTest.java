package com.grabit.app.serviceTests;

import com.grabit.app.service.ProjectService;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProjectService projectService;
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project(1, "Project 1", "Description", new Date(), new Date());
        new ProjectCollaborator();
        new Task();
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(project)).thenReturn(project);

        Project createdProject = projectService.createProject(project, null);

        assertNotNull(createdProject);
        assertEquals("Project 1", createdProject.getProjectName());
        assertEquals("Description", createdProject.getProjectDescription());

        verify(projectRepository, times(1)).save(project);
    }


    @Test
    void testGetProjectById() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        Project result = projectService.getProjectByID(1);
        assertNotNull(result);
        assertEquals("Project 1", result.getProjectName());

        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectRepository.findById(999)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> projectService.getProjectByID(999));
        assertEquals("Project not found", exception.getMessage());

        verify(projectRepository, times(1)).findById(999);
    }

    @Test
    void testCloseProject() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        projectService.closeProject(1);

        verify(projectRepository, times(1)).deleteById(1);
    }

    @Test
    void testCloseProjectNotFound() {
        when(projectRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> projectService.closeProject(999));
        assertEquals("Project not found", exception.getMessage());

        verify(projectRepository, times(1)).findById(999);
    }
}
