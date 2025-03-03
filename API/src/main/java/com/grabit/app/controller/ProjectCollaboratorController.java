package com.grabit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.UserService;

@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {

    @Autowired
    private ProjectCollaboratorService projectCollaboratorService;

    @Autowired
    private UserService userService;

    @GetMapping("/{projectCollabID}")
    public ProjectCollaborator getProjectCollaboratorByID(@PathVariable Integer projectCollabID) {
        return projectCollaboratorService.getProjectCollaboratorByID(projectCollabID);
    }

    @DeleteMapping("/{projectCollabID}")
    public void deactivateProjectCollaborator(@PathVariable Integer projectCollabID) {
        projectCollaboratorService.deactivateProjectCollaborator(projectCollabID);
    }

    @PostMapping
    public ResponseEntity<Void> createProjectCollaborator(@RequestBody ProjectCollaborator projectCollaborator,
            Authentication authentication) {
        boolean isDuplicate = projectCollaboratorService.exists(
                projectCollaborator.getUserID(),
                projectCollaborator.getProjectID(),
                projectCollaborator.getRoleID());
        if (isDuplicate) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        projectCollaboratorService.addProjectCollaborator(projectCollaborator,
                userService.getAuthenticatedUser(authentication));
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
