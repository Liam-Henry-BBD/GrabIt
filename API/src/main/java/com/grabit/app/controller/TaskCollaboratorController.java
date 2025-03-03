package com.grabit.app.controller;

import com.grabit.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.service.TaskCollaboratorService;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<Void> createTaskCollaborator(@RequestBody TaskCollaborator taskCollaborator, Authentication authentication) {
        taskCollaboratorService.addTaskCollaborator(taskCollaborator, userService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{taskCollabID}")
    public ResponseEntity<TaskCollaborator> getTaskCollaboratorByID(@PathVariable Integer taskCollabID, Authentication authentication) {
        TaskCollaborator taskCollaborator = taskCollaboratorService.getTaskCollaboratorByID(taskCollabID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.ok().body(taskCollaborator);
    }

    @DeleteMapping("/{taskCollabID}")
    public ResponseEntity<TaskCollaborator> deactivateTaskCollaboratorByID(@PathVariable Integer taskCollabID, Authentication authentication) {
        TaskCollaborator collaborator = taskCollaboratorService.deactivateTaskCollaboratorByID(taskCollabID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.accepted().body(collaborator);
    }

    @PutMapping("/{taskCollabID}/activate")
    public ResponseEntity<TaskCollaborator> activateTaskCollaboratorByID(@PathVariable Integer taskCollabID, Authentication authentication) {
        TaskCollaborator updatedCollaborator = taskCollaboratorService.activateTaskCollaboratorByID(taskCollabID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.accepted().body(updatedCollaborator);
    }

}
