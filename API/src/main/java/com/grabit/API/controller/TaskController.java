package com.grabit.api.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grabit.api.model.Task;
import com.grabit.api.model.TaskCollaborator;
import com.grabit.api.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskID}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer taskID) {
        return ResponseEntity.ok(taskService.getTaskById(taskID));
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskID) {
        taskService.deleteTask(taskID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskID, @Valid @RequestBody Task task) {
        return ResponseEntity.accepted().body(taskService.updateTask(taskID, task));
    }

    @GetMapping("/{taskID}/collaborators")
    public ResponseEntity<List<TaskCollaborator>> getCollaboratorByTaskID(@PathVariable Integer taskID) {
        return ResponseEntity.ok(taskService.getTaskCollaborators(taskID));
    }

    @PutMapping("/{taskID}/status/{taskStatusID}")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Integer taskID, @PathVariable Byte taskStatusID) {
        return ResponseEntity.accepted().body(taskService.updateTaskStatus(taskID, taskStatusID));
    }
}