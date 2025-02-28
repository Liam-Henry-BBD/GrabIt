package com.grabit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;

@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {

    @Autowired
    private ProjectCollaboratorService projectCollaboratorService;

    @GetMapping("/{projectCollabID}")
    public ProjectCollaborator getProjectCollaboratorByID(@PathVariable Long projectCollabID) {
        return projectCollaboratorService.getProjectCollaboratorByID(projectCollabID);
    }

    @DeleteMapping("/{projectCollabID}")
    public void deactivateProjectCollaborator(@PathVariable Long projectCollabID) {
        projectCollaboratorService.deactivateProjectCollaborator(projectCollabID);
    }

    @PostMapping
    public ResponseEntity<Void> createProjectCollaborator(@RequestBody ProjectCollaborator projectCollaborator) {
        boolean isDuplicate = projectCollaboratorService.exists(
            projectCollaborator.getUserID().longValue(), 
            projectCollaborator.getProjectID().longValue(), 
            projectCollaborator.getRoleID().longValue()
        );
        if(isDuplicate) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        projectCollaboratorService.addProjectCollaborator(projectCollaborator);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}


