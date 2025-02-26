package com.grabit.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.grabit.api.model.ProjectCollaborator;
import com.grabit.api.service.ProjectCollaboratorService;

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
