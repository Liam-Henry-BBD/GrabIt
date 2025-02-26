package com.grabit.ModelTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.model.Role;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class TaskCollaboratorTest {

    @Test
    public void testTaskCollaboratorConstructorAndGetters() {
        User user = new User();
        Role role = new Role((byte) 1, "Admin");
        Task task = new Task();
        LocalDate joinedAt = LocalDate.now();
        TaskCollaborator taskCollaborator = new TaskCollaborator(1, user, role, task, joinedAt, true);

        assertEquals(1, taskCollaborator.getTaskCollaboratorId());
        assertEquals(user, taskCollaborator.getUser());
        assertEquals(role, taskCollaborator.getRole());
        assertEquals(task, taskCollaborator.getTask());
        assertEquals(joinedAt, taskCollaborator.getJoinedAt());
        assertTrue(taskCollaborator.getIsActive());
    }

    @Test
    public void testTaskCollaboratorSetters() {
        TaskCollaborator taskCollaborator = new TaskCollaborator();
        User user = new User();
        Role role = new Role((byte) 2, "User");
        Task task = new Task();
        LocalDate joinedAt = LocalDate.now();

        taskCollaborator.setTaskCollaboratorId(2);
        assertEquals(2, taskCollaborator.getTaskCollaboratorId());

        taskCollaborator.setUser(user);
        assertEquals(user, taskCollaborator.getUser());

        taskCollaborator.setRole(role);
        assertEquals(role, taskCollaborator.getRole());

        taskCollaborator.setTask(task);
        assertEquals(task, taskCollaborator.getTask());

        taskCollaborator.setJoinedAt(joinedAt);
        assertEquals(joinedAt, taskCollaborator.getJoinedAt());

        taskCollaborator.setIsActive(false);
        assertFalse(taskCollaborator.getIsActive());
    }
}