package com.grabit.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grabit.API.model.TaskCollaborators;
import com.grabit.API.service.TaskCollaboratorsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taskCollaborators")
public class TaskCollaboratorsController {

    private final TaskCollaboratorsService taskCollaboratorsService;

    @Autowired
    public TaskCollaboratorsController(TaskCollaboratorsService taskCollaboratorsService) {
        this.taskCollaboratorsService = taskCollaboratorsService;
    }

    // Creating/updating a task collaborator.
    @PostMapping
    public ResponseEntity<TaskCollaborators> createTaskCollaborators(@RequestBody TaskCollaborators taskCollaborators) {
        TaskCollaborators savedTaskCollaborators = taskCollaboratorsService.addTaskCollaborators(taskCollaborators);
        return new ResponseEntity<>(savedTaskCollaborators, HttpStatus.CREATED);
    }

    // Getting all task collaborators.
    @GetMapping
    public List<TaskCollaborators> getAllTaskCollaborators() {
        return taskCollaboratorsService.getAllTaskCollaborators();
    }

    // Getting task collaborator by their ID.
    @GetMapping("/{id}")
    public ResponseEntity<TaskCollaborators> getTaskCollaboratorsByID(@PathVariable Integer id) {
        Optional<TaskCollaborators> taskCollaborators = Optional.ofNullable(taskCollaboratorsService.getTaskCollaboratorsByID(id));
        return taskCollaborators.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deleting task collaborator by their ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskCollaboratorsByID(@PathVariable Integer id) {
        taskCollaboratorsService.deleteTaskCollaboratorsByID(id);
        return ResponseEntity.noContent().build();
    }
}
