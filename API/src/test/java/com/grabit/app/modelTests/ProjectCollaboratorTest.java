package com.grabit.app.modelTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.model.ProjectCollaborator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ProjectCollaboratorTest {

    @Test
    public void testProjectCollaboratorConstructorAndGetters() {
        LocalDateTime joinedAt = LocalDateTime.now();
        ProjectCollaborator projectCollaborator = new ProjectCollaborator(1, 2, 3, 4, joinedAt, true);

        assertEquals(1, projectCollaborator.getProjectCollaboratorID());
        assertEquals(2, projectCollaborator.getUserID());
        assertEquals(3, projectCollaborator.getProjectID());
        assertEquals(4, projectCollaborator.getRoleID());
        assertEquals(joinedAt, projectCollaborator.getJoinedAt());
        assertTrue(projectCollaborator.isActive());
    }

    @Test
    public void testProjectCollaboratorSetters() {
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        LocalDateTime joinedAt = LocalDateTime.now();

        projectCollaborator.setProjectCollaboratorID(1);
        assertEquals(1, projectCollaborator.getProjectCollaboratorID());

        projectCollaborator.setUserID(2);
        assertEquals(2, projectCollaborator.getUserID());

        projectCollaborator.setProjectID(3);
        assertEquals(3, projectCollaborator.getProjectID());

        projectCollaborator.setRoleID(4);
        assertEquals(4, projectCollaborator.getRoleID());

        projectCollaborator.setJoinedAt(joinedAt);
        assertEquals(joinedAt, projectCollaborator.getJoinedAt());

        projectCollaborator.setActive(true);
        assertTrue(projectCollaborator.isActive());
    }
}
