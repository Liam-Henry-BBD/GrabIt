package com.grabit.API.dataTransferObjectTests;

import com.grabit.API.dataTransferObject.ProjectCreationDTO;
import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaborator;
import org.junit.jupiter.api.Test;

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
