package com.grabit.app.modelTests;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import com.grabit.app.model.ProjectCollaborator;

import static org.junit.jupiter.api.Assertions.*;

class ProjectCollaboratorTest {

    @Test
    void testProjectCollaboratorConstructorAndGettersSetters() {

        Integer projectCollaboratorID = 1;
        Integer userID = 100;
        Integer projectID = 200;
        Byte roleID = 1;
        LocalDateTime joinedAt = LocalDateTime.now();
        boolean isActive = true;
        ProjectCollaborator collaborator = new ProjectCollaborator(projectCollaboratorID, userID, projectID, roleID, joinedAt, isActive);

        assertEquals(projectCollaboratorID, collaborator.getProjectCollaboratorID());
        assertEquals(userID, collaborator.getUserID());
        assertEquals(projectID, collaborator.getProjectID());
        assertEquals(roleID, collaborator.getRoleID());
        assertEquals(joinedAt, collaborator.getJoinedAt());
        assertEquals(isActive, collaborator.isActive());
    }

    @Test
    void testProjectCollaboratorSettersAndGetters() {

        ProjectCollaborator collaborator = new ProjectCollaborator();
        collaborator.setProjectCollaboratorID(1);
        collaborator.setUserID(100);
        collaborator.setProjectID(200);
        collaborator.setRoleID((byte) 1);
        collaborator.setJoinedAt(LocalDateTime.now());
        collaborator.setActive(true);

        assertEquals(1, collaborator.getProjectCollaboratorID());
        assertEquals(100, collaborator.getUserID());
        assertEquals(200, collaborator.getProjectID());
        assertEquals((byte) 1, collaborator.getRoleID());
        assertNotNull(collaborator.getJoinedAt()); 
        assertTrue(collaborator.isActive());
    }

    @Test
    void testDefaultConstructor() {
        
        ProjectCollaborator collaborator = new ProjectCollaborator();
        assertNotNull(collaborator);  
        assertNull(collaborator.getProjectCollaboratorID());  
        assertFalse(collaborator.isActive()); 
        assertNull(collaborator.getJoinedAt()); 
    }
}
