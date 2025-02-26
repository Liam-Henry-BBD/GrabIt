package com.grabit.app.modelTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.model.TaskPoint;

import static org.junit.jupiter.api.Assertions.*;

public class TaskPointTest {

    @Test
    public void testTaskPointConstructorAndGetters() {
        TaskPoint taskPoint = new TaskPoint((byte) 1, "Easy");

        assertEquals((byte) 1, taskPoint.getTaskPointID());
        assertEquals("Easy", taskPoint.getTaskDifficulty());
    }

    @Test
    public void testTaskPointSetters() {
        TaskPoint taskPoint = new TaskPoint();

        taskPoint.setTaskPointID((byte) 2);
        assertEquals((byte) 2, taskPoint.getTaskPointID());

        taskPoint.setTaskDifficulty("Medium");
        assertEquals("Medium", taskPoint.getTaskDifficulty());
    }
}