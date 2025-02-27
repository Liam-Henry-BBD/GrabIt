package com.grabit.app.serviceTests;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectCollaboratorServiceTest {

    @Mock
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    @InjectMocks
    private ProjectCollaboratorService projectCollaboratorService;

    private ProjectCollaborator collaborator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        collaborator = new ProjectCollaborator(
                1,
                101,
                202,
                303,
                LocalDateTime.of(2023, 1, 1, 10, 0),
                true);
    }

    @Test
    void testGetAllProjectCollaboratorsByProjectID() {
        List<ProjectCollaborator> collaborators = Arrays.asList(collaborator);
        when(projectCollaboratorRepository.findByProjectID(202)).thenReturn(collaborators);

        List<ProjectCollaborator> result = projectCollaboratorService.getAllProjectCollaboratorsByProjectID(202);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getUserID());
        assertEquals(202, result.get(0).getProjectID());

        verify(projectCollaboratorRepository, times(1)).findByProjectID(202);
    }

    @Test
    void testAddProjectCollaborator() {
        projectCollaboratorService.addProjectCollaborator(collaborator);
        verify(projectCollaboratorRepository, times(1)).insertCollaborator(
            collaborator.getJoinedAt(), 
            collaborator.getUserID(), 
            collaborator.getRoleID(), 
            collaborator.getProjectID()
        );
    }

    @Test
    void testGetProjectCollaboratorByID() {
        when(projectCollaboratorRepository.findById(1)).thenReturn(Optional.of(collaborator));
        ProjectCollaborator result = projectCollaboratorService.getProjectCollaboratorByID(1L);

        assertNotNull(result);
        assertEquals(101, result.getUserID());
        assertEquals(202, result.getProjectID());

        verify(projectCollaboratorRepository, times(1)).findById(1);
    }

    @Test
    void testGetProjectCollaboratorByIDNotFound() {
        when(projectCollaboratorRepository.findById(999)).thenReturn(Optional.empty());
        ProjectCollaborator result = projectCollaboratorService.getProjectCollaboratorByID(999L);

        assertNull(result);

        verify(projectCollaboratorRepository, times(1)).findById(999);
    }

    @Test
    void testDeactivateProjectCollaborator() {
        projectCollaboratorService.deactivateProjectCollaborator(1L);
        verify(projectCollaboratorRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllActiveProjectCollaborators() {
        List<ProjectCollaborator> activeCollaborators = Arrays.asList(collaborator);
        when(projectCollaboratorRepository.findByIsActive(true)).thenReturn(activeCollaborators);

        List<ProjectCollaborator> result = projectCollaboratorService.getAllActiveProjectCollaborators();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isActive());

        verify(projectCollaboratorRepository, times(1)).findByIsActive(true);
    }
}
