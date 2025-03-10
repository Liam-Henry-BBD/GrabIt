package com.grabit.app.serviceTests;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;
import org.junit.jupiter.api.extension.ExtendWith;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectCollaboratorServiceTests {

    @Mock
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectCollaboratorService projectCollaboratorService;

    private ProjectCollaborator collaborator;
    private User user;

    @BeforeEach
    void setUp() {
        collaborator = new ProjectCollaborator(1, 100, 200, (byte) 1, LocalDateTime.now(), true);
        user = new User();
        user.setUserID(100);
    }

    @Test
    void testGetAllProjectCollaboratorsByProjectID() {
        List<ProjectCollaborator> collaborators = Arrays.asList(collaborator);
        when(projectCollaboratorRepository.findByProjectID(200)).thenReturn(collaborators);

        List<ProjectCollaborator> result = projectCollaboratorService.getAllProjectCollaboratorsByProjectID(200);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(collaborator, result.get(0));
    }

//    @Test
//    void testAddProjectCollaborator() {
//        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
//
//        doNothing().when(projectCollaboratorRepository).insertCollaborator(any(), anyInt(), any(), anyInt());
//        projectCollaboratorService.addProjectCollaborator(collaborator, user);
//
//        verify(projectCollaboratorRepository, times(1)).insertCollaborator(any(), eq(100), eq((byte) 1), eq(200));
//    }

    @Test
    void testGetProjectCollaboratorByID() {
        when(projectCollaboratorRepository.findById(1)).thenReturn(Optional.of(collaborator));
        ProjectCollaborator result = projectCollaboratorService.getProjectCollaboratorByID(1);

        assertNotNull(result);
        assertEquals(collaborator, result);
    }

    @Test
    void testDeactivateProjectCollaborator() {
        doNothing().when(projectCollaboratorRepository).deleteById(1);
        projectCollaboratorService.deactivateProjectCollaborator(1);

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
    }

    @Test
    void testExists() {
        when(projectCollaboratorRepository.existsByUserIDAndProjectID(100, 200))
                .thenReturn(true);

        boolean result = projectCollaboratorService.exists(100, 200);

        assertTrue(result);
    }
}
