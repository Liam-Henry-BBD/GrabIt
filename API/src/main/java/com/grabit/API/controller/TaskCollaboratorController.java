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

    // Creating a task collaborator.
    @PostMapping
    public ResponseEntity<TaskCollaborator> createTaskCollaborator(@RequestBody TaskCollaborator taskCollaborator) {
        TaskCollaborator savedTaskCollaborator = taskCollaboratorService.addTaskCollaborator(taskCollaborator);
        return new ResponseEntity<>(savedTaskCollaborator, HttpStatus.CREATED);
    }

    // Getting all task collaborators in all tasks.
    @GetMapping
    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorService.getAllTaskCollaborators();
    }

    // Getting task collaborator by their ID.
    @GetMapping("/{id}")
    public ResponseEntity<TaskCollaborator> getTaskCollaboratorByID(@PathVariable Integer id) {
        Optional<TaskCollaborator> taskCollaborator = Optional.ofNullable(taskCollaboratorService.getTaskCollaboratorByID(id));
        return taskCollaborator.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deleting task collaborator by their ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskCollaboratorByID(@PathVariable Integer id) {
        taskCollaboratorService.deleteTaskCollaboratorByID(id);
        return ResponseEntity.noContent().build();
    }

    // Getting all collaborators for a specific task.
    @GetMapping("/task/{taskID}")
    public ResponseEntity<List<TaskCollaborator>> getCollaboratorsByTaskID(@PathVariable Integer taskID) {
        List<TaskCollaborator> collaborators = taskCollaboratorService.getCollaboratorsByTaskID(taskID);
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }
}
