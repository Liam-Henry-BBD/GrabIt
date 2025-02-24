package com.grabit.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.service.TaskCollaboratorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taskCollaborators")
public class TaskCollaboratorController {

    private final TaskCollaboratorService taskCollaboratorService;

    @Autowired
    public TaskCollaboratorController(TaskCollaboratorService taskCollaboratorService) {
        this.taskCollaboratorService = taskCollaboratorService;
    }

    // Creating a task collaborator
    @PostMapping
    public ResponseEntity<TaskCollaborator> addTaskCollaborator(@RequestBody TaskCollaborator taskCollaborator) {
        TaskCollaborator savedCollaborator = taskCollaboratorService.addTaskCollaborator(taskCollaborator);
        return new ResponseEntity<>(savedCollaborator, HttpStatus.CREATED);
    }

    // Getting all task collaborators
    @GetMapping
    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorService.getAllTaskCollaborators();
    }

    // Getting task collaborator by their ID
    @GetMapping("/{taskCollabID}")
    public ResponseEntity<TaskCollaborator> getTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        Optional<TaskCollaborator> taskCollaborator = Optional.ofNullable(taskCollaboratorService.getTaskCollaboratorByID(taskCollabID));
        return taskCollaborator.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deactivating task collaborator by their ID
    @DeleteMapping("/{taskCollabID}")
    public ResponseEntity<Void> deactivateTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        taskCollaboratorService.deactivateTaskCollaboratorByID(taskCollabID);
        return ResponseEntity.accepted().build();
    }

    // Activating task collaborator by their ID
    @PutMapping("/{taskCollabID}/activate")
    public ResponseEntity<Void> activateTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        taskCollaboratorService.activateTaskCollaboratorByID(taskCollabID);
        return ResponseEntity.ok().build();
    }

    // Getting all collaborators for a specific task
    @GetMapping("/task/{taskID}")
    public ResponseEntity<List<TaskCollaborator>> getCollaboratorByTaskID(@PathVariable Integer taskID) {
        List<TaskCollaborator> collaborators = taskCollaboratorService.getCollaboratorByTaskID(taskID);
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }
}
