package com.grabit.ModelTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.model.Project;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class ProjectTest {
    @Test
    public void testProjectConstructorAndGetters(){
        Date createdAt = new Date();
        Date updatedAt = new Date();
        Project project = new Project(1, "ProjectName", "ProjectDescription", createdAt, updatedAt);
        
        assertEquals(1, project.getProjectID());
        assertEquals("ProjectName", project.getProjectName());
        assertEquals("ProjectDescription", project.getProjectDescription());
        assertEquals(createdAt, project.getCreatedAt());
        assertEquals(updatedAt, project.getUpdatedAt());
    }

    @Test
    public void testProjectSetters(){
        Project project = new Project();
        project.setProjectName("NewProjectName");
        project.setProjectDescription("NewProjectDescription");
        Date newCreatedAt = new Date();
        project.setCreatedAt(newCreatedAt);
        Date newUpdatedAt = new Date();
        project.setUpdatedAt(newUpdatedAt);

        assertEquals("NewProjectName", project.getProjectName());
        assertEquals("NewProjectDescription", project.getProjectDescription());
        assertEquals(newCreatedAt, project.getCreatedAt());
        assertEquals(newUpdatedAt, project.getUpdatedAt());
    }
}
