package com.grabit.app.controller;

import com.grabit.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.service.TaskCollaboratorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task-collaborators")
public class TaskCollaboratorController {

    private final TaskCollaboratorService taskCollaboratorService;
    private final UserService userService;
    public TaskCollaboratorController(TaskCollaboratorService taskCollaboratorService,
                                      UserService userService) {
        this.taskCollaboratorService = taskCollaboratorService;
        this.userService = userService;
    }

    @GetMapping
    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorService.getAllTaskCollaborators();
    }

    @PostMapping
    public ResponseEntity<Void> createTaskCollaborator(@RequestBody TaskCollaborator taskCollaborator, Authentication authentication) {
        taskCollaboratorService.addTaskCollaborator(taskCollaborator);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
