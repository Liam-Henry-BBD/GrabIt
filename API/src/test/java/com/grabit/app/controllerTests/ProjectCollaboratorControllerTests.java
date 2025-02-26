package com.grabit.app.controllerTests;

import com.grabit.app.controller.ProjectCollaboratorController;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProjectCollaboratorControllerTests {

    @Mock
    private ProjectCollaboratorService projectCollaboratorService;

    @InjectMocks
    private ProjectCollaboratorController projectCollaboratorController;

    private ProjectCollaborator projectCollaborator;

    @BeforeEach
    public void setUp() {
        projectCollaborator = new ProjectCollaborator();
    }

    @Test
    public void testGetProjectCollaboratorByID() {
        projectCollaboratorService.getProjectCollaboratorByID(3L);
        ProjectCollaborator response = projectCollaboratorController.getProjectCollaboratorByID(1L);
//        assertThat(response).isEqualTo(projectCollaborator);
        verify(projectCollaboratorService, times(1)).getProjectCollaboratorByID(1L);
    }

    @Test
    public void testDeactivateProjectCollaborator() {
        projectCollaboratorService.deactivateProjectCollaborator(1L);
        projectCollaboratorController.deactivateProjectCollaborator(1L);
//        verify(projectCollaboratorService, times(1)).deactivateProjectCollaborator(1L);
    }
}
