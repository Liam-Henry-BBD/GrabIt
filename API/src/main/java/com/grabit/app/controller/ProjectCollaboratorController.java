package com.grabit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;

@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {

    @Autowired
    private ProjectCollaboratorService projectCollaboratorService;

    @GetMapping("/{projectCollabID}")
    public ProjectCollaborator getProjectCollaboratorByID(@PathVariable Long id) {
        return projectCollaboratorService.getProjectCollaboratorByID(id);
    }

    @DeleteMapping("/{projectCollabID}")
    public void deactivateProjectCollaborator(@PathVariable Long id) {
        projectCollaboratorService.deactivateProjectCollaborator(id);
    }
}
