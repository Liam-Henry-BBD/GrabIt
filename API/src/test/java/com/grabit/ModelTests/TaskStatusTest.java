package com.grabit.ModelTests;

import com.grabit.API.model.TaskStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    private TaskStatus taskStatus;

    @BeforeEach
    void setUp() {
        taskStatus = new TaskStatus((byte) 1, "In Progress");
    }

    @Test
    void testConstructor() {
        assertEquals(1, taskStatus.getTaskStatusID());
        assertEquals("In Progress", taskStatus.getStatusName());
    }

    @Test
    void testSettersAndGetters() {
        taskStatus.setTaskStatusID((byte) 2);
        taskStatus.setStatusName("Completed");

        assertEquals(2, taskStatus.getTaskStatusID());
        assertEquals("Completed", taskStatus.getStatusName());
    }

    @Test
    void testNoArgsConstructor() {
        TaskStatus taskStatusNoArgs = new TaskStatus();
        assertNotNull(taskStatusNoArgs);
    }

    @Test
    void testAllArgsConstructor() {
        TaskStatus taskStatusAllArgs = new TaskStatus((byte) 2, "Pending");
        assertEquals(2, taskStatusAllArgs.getTaskStatusID());
        assertEquals("Pending", taskStatusAllArgs.getStatusName());
    }
}

