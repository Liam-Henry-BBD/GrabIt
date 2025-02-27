package com.grabit.app.serviceTests;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.service.ProjectCollaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.Arrays;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProjectCollaboratorServiceTest {

    @Mock
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    @InjectMocks
    private ProjectCollaboratorService projectCollaboratorService;

    private ProjectCollaborator projectCollaborator;

    @BeforeEach
    public void setUp() {
        projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProjectID(1);
        projectCollaborator.setUserID(1);
        projectCollaborator.setRoleID(1);
        projectCollaborator.setJoinedAt(LocalDateTime.parse("2025-02-27T00:00:00"));
    }

    @Test
    public void testGetAllProjectCollaboratorsByProjectID() {
        when(projectCollaboratorRepository.findByProjectID(1)).thenReturn(Arrays.asList(projectCollaborator));
        var collaborators = projectCollaboratorService.getAllProjectCollaboratorsByProjectID(1);

        assertNotNull(collaborators);
        assertEquals(1, collaborators.size());
        verify(projectCollaboratorRepository, times(1)).findByProjectID(1);
    }

    @Test
    public void testAddProjectCollaborator() {
        when(projectCollaboratorRepository.findByProjectID(anyInt())).thenReturn(Arrays.asList(projectCollaborator));
        doNothing().when(projectCollaboratorRepository).insertCollaborator(any(), any(), any(), anyInt());

        projectCollaboratorService.addProjectCollaborator(projectCollaborator);
        verify(projectCollaboratorRepository, times(1)).insertCollaborator(any(), any(), any(), anyInt());
    }

    @Test
    public void testGetProjectCollaboratorByID() {
        when(projectCollaboratorRepository.findById(anyInt())).thenReturn(Optional.of(projectCollaborator));
        ProjectCollaborator result = projectCollaboratorService.getProjectCollaboratorByID(1L);

        assertNotNull(result);
        assertEquals(1, result.getProjectID());
        verify(projectCollaboratorRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testGetProjectCollaboratorByID_NotFound() {
        when(projectCollaboratorRepository.findById(anyInt())).thenReturn(Optional.empty());

        ProjectCollaborator result = projectCollaboratorService.getProjectCollaboratorByID(1L);

        assertNull(result);
        verify(projectCollaboratorRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testDeactivateProjectCollaborator() {
        doNothing().when(projectCollaboratorRepository).deleteById(anyInt());

        projectCollaboratorService.deactivateProjectCollaborator(1L);

        verify(projectCollaboratorRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testGetAllActiveProjectCollaborators() {
        when(projectCollaboratorRepository.findByIsActive(true)).thenReturn(Arrays.asList(projectCollaborator));
        var collaborators = projectCollaboratorService.getAllActiveProjectCollaborators();

        assertNotNull(collaborators);
        assertEquals(1, collaborators.size());
        verify(projectCollaboratorRepository, times(1)).findByIsActive(true);
    }

    @Test
    public void testExists() {
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(anyInt(), anyInt(), anyInt())).thenReturn(true);
        boolean exists = projectCollaboratorService.exists(1L, 1L, 1L);

        assertTrue(exists);
        verify(projectCollaboratorRepository, times(1)).existsByUserIDAndProjectIDAndRoleID(anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testExists_False() {
        when(projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(anyInt(), anyInt(), anyInt())).thenReturn(false);
        boolean exists = projectCollaboratorService.exists(1L, 1L, 1L);

        assertFalse(exists);
        verify(projectCollaboratorRepository, times(1)).existsByUserIDAndProjectIDAndRoleID(anyInt(), anyInt(), anyInt());
    }
}
