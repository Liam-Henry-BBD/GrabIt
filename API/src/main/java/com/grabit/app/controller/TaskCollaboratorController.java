package com.grabit.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.service.TaskCollaboratorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task-collaborators")
public class TaskCollaboratorController {

    private final TaskCollaboratorService taskCollaboratorService;

    public TaskCollaboratorController(TaskCollaboratorService taskCollaboratorService) {
        this.taskCollaboratorService = taskCollaboratorService;
    }

    @GetMapping
    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorService.getAllTaskCollaborators();
    }

    @GetMapping("/{taskCollabID}")
    public ResponseEntity<TaskCollaborator> getTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        Optional<TaskCollaborator> taskCollaborator = Optional
                .ofNullable(taskCollaboratorService.getTaskCollaboratorByID(taskCollabID));
        return taskCollaborator.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskCollabID}")
    public ResponseEntity<Void> deactivateTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        taskCollaboratorService.deactivateTaskCollaboratorByID(taskCollabID);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{taskCollabID}/activate")
    public ResponseEntity<Void> activateTaskCollaboratorByID(@PathVariable Integer taskCollabID) {
        taskCollaboratorService.activateTaskCollaboratorByID(taskCollabID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/task/{taskID}")
    public ResponseEntity<List<TaskCollaborator>> getCollaboratorByTaskID(@PathVariable Integer taskID) {
        List<TaskCollaborator> collaborators = taskCollaboratorService.getCollaboratorByTaskID(taskID);
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }
}
