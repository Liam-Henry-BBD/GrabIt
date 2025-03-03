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
        projectCollaborator.setProjectID(1);
    }

    @Test
    public void testGetProjectCollaboratorByID() {
        projectCollaboratorController.getProjectCollaboratorByID(1);

        verify(projectCollaboratorService, times(1)).getProjectCollaboratorByID(1);
    }

    @Test
    public void testGetProjectCollaboratorsByProjectID() {
        projectCollaboratorController.getProjectCollaboratorsByProjectID(1L);

        verify(projectCollaboratorService, times(1)).getProjectCollaboratorsByProjectID(1L);
    }
}
