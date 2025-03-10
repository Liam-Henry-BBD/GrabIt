package com.grabit.app.dtoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.grabit.app.dto.ProjectCreationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectCreationDTOTests {

    @Test
    public void testProjectCreationDTO() {

        String projectName = "Project A";
        String projectDescription = "This is a test project description for Project A";

        ProjectCreationDTO project = new ProjectCreationDTO(projectName, projectDescription);

        assertEquals(projectName, project.getProjectName());
        assertEquals(projectDescription, project.getProjectDescription());
    }
}
