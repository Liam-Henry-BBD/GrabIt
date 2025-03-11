package com.grabit.app.controller;

import com.grabit.app.dto.TaskDTO;
import com.grabit.app.service.UserService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grabit.app.dto.CreateResponseDTO;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{taskID}")
    public ResponseEntity<TaskDTO> getTaskByID(@PathVariable Integer taskID, Authentication authentication) {
        TaskDTO task = taskService.getTaskById(taskID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskID, Authentication authentication) {
        taskService.deleteTask(taskID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createTask(@Valid @RequestBody Task task, Authentication authentication) {
        taskService.createTask(task, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.ok(new CreateResponseDTO("Task created successfully", 201));
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskID, @Valid @RequestBody Task task,
            Authentication authentication) {
        return ResponseEntity.accepted()
                .body(taskService.updateTask(taskID, task, userService.getAuthenticatedUser(authentication)));
    }

    @GetMapping("/{taskID}/collaborators")
    public ResponseEntity<List<TaskCollaborator>> getCollaboratorByTaskID(@PathVariable Integer taskID,
            Authentication authentication) {
        return ResponseEntity
                .ok(taskService.getTaskCollaborators(taskID, userService.getAuthenticatedUser(authentication)));
    }

    @PutMapping("/{taskID}/status/{taskStatusID}")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Integer taskID, @PathVariable Byte taskStatusID,
            Authentication authentication) {
        return ResponseEntity.accepted().body(
                taskService.updateTaskStatus(taskID, taskStatusID, userService.getAuthenticatedUser(authentication)));
    }

    @PostMapping("/grab/{taskID}")
    public ResponseEntity<Task> grabTask(@PathVariable Integer taskID,
            Authentication authentication) {
        Task grabbedTask = taskService.grabTask(taskID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.accepted().body(grabbedTask);
    }

}