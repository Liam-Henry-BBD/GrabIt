package com.grabit.app.serviceTests;

import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectCreationDTO projectCreationDTO;
    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project(1, "Project 1", "Description", new Date(), new Date(), true);
        user = new User();
        projectCreationDTO = new ProjectCreationDTO(project.getProjectName(), project.getProjectDescription());
        projectCreationDTO.setProjectName("Project 1");
        projectCreationDTO.setProjectDescription("Description");
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

        assertThrows(RuntimeException.class, () -> projectService.getProjectByID(999));
        verify(projectRepository, times(1)).findById(999);
    }

    @Test
    void testCloseProjectNotFound() {
        when(projectRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.closeProject(999));
        verify(projectRepository, times(1)).findById(999);
    }
}
