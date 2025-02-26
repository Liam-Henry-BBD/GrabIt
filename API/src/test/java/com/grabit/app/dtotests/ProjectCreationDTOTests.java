package com.grabit.app.dtotests;

import org.junit.jupiter.api.Test;

import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectCreationDTOTests {

    @Test
    public void testGettersAndSetters() {
        Project project = new Project();
        ProjectCollaborator collaborator = new ProjectCollaborator();
        ProjectCreationDTO dto = new ProjectCreationDTO(project, collaborator);

        dto.setProject(project);
        dto.setProjectCollaborator(collaborator);

        assertEquals(project, dto.getProject());
        assertEquals(collaborator, dto.getProjectCollaborator());
    }
}
