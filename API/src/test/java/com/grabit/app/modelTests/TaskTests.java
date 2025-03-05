package com.grabit.app.modelTests;
import org.junit.jupiter.api.Test;

import com.grabit.app.model.Project;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskPoint;
import com.grabit.app.model.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskTests {

    @Test
    public void testTaskConstructorAndGetters() {
        Project project = new Project(1, "ProjectName", "ProjectDescription", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        TaskPoint taskPoint = new TaskPoint();
        TaskStatus taskStatus = new TaskStatus();
        LocalDateTime createdAt = LocalDateTime.now();
        Task task = new Task(1, project, taskPoint, taskStatus, "TaskName", "TaskDescription", LocalDate.now(), createdAt, new Date(System.currentTimeMillis()), LocalDateTime.now(), new Date(System.currentTimeMillis()));

        assertEquals(1, task.getTaskID());
        assertEquals(project, task.getProject());
        assertEquals(taskPoint, task.getTaskPoint());
        assertEquals(taskStatus, task.getTaskStatus());
        assertEquals("TaskName", task.getTaskName());
        assertEquals("TaskDescription", task.getTaskDescription());
        assertEquals(LocalDate.now(), task.getTaskDeadline());
        assertEquals(createdAt, task.getTaskCreatedAt());
    }

    @Test
    public void testTaskSetters() {
        Task task = new Task();
        Project project = new Project(2, "NewProjectName", "NewProjectDescription", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        TaskPoint taskPoint = new TaskPoint();
        TaskStatus taskStatus = new TaskStatus();
        LocalDateTime newCreatedAt = LocalDateTime.now();
        task.setTaskID(2);
        task.setProject(project);
        task.setTaskPoint(taskPoint);
        task.setTaskStatus(taskStatus);
        task.setTaskName("NewTaskName");
        task.setTaskDescription("NewTaskDescription");
        task.setTaskDeadline(LocalDate.now());
        task.setTaskCreatedAt(newCreatedAt);
        task.setTaskUpdatedAt(new Date(System.currentTimeMillis()));
        task.setTaskReviewRequestedAt(LocalDateTime.now());
        task.setTaskCompletedAt(new Date(System.currentTimeMillis()));

        assertEquals(2, task.getTaskID());
        assertEquals(project, task.getProject());
        assertEquals(taskPoint, task.getTaskPoint());
        assertEquals(taskStatus, task.getTaskStatus());
        assertEquals("NewTaskName", task.getTaskName());
        assertEquals("NewTaskDescription", task.getTaskDescription());
        assertEquals(LocalDate.now(), task.getTaskDeadline());
        assertEquals(newCreatedAt, task.getTaskCreatedAt());
    }

    @Test
    public void testTaskSettersIndividually() {
        Task task = new Task();
        Project project = new Project(2, "NewProjectName", "NewProjectDescription", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        TaskPoint taskPoint = new TaskPoint();
        TaskStatus taskStatus = new TaskStatus();
        LocalDateTime newCreatedAt = LocalDateTime.now();

        task.setTaskID(2);
        assertEquals(2, task.getTaskID());

        task.setProject(project);
        assertEquals(project, task.getProject());

        task.setTaskPoint(taskPoint);
        assertEquals(taskPoint, task.getTaskPoint());

        task.setTaskStatus(taskStatus);
        assertEquals(taskStatus, task.getTaskStatus());

        task.setTaskName("NewTaskName");
        assertEquals("NewTaskName", task.getTaskName());

        task.setTaskDescription("NewTaskDescription");
        assertEquals("NewTaskDescription", task.getTaskDescription());

        task.setTaskDeadline(LocalDate.now());
        assertEquals(LocalDate.now(), task.getTaskDeadline());

        task.setTaskCreatedAt(newCreatedAt);
        assertEquals(newCreatedAt, task.getTaskCreatedAt());

        task.setTaskUpdatedAt(new Date(System.currentTimeMillis()));
        assertEquals(new Date(System.currentTimeMillis()), task.getTaskUpdatedAt());

        task.setTaskCompletedAt(new Date(System.currentTimeMillis()));
        assertEquals(new Date(System.currentTimeMillis()), task.getTaskCompletedAt());
    }
}