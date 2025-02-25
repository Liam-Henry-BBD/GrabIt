package com.grabit.API.controller;

import com.grabit.API.model.ProjectCollaborator;
import com.grabit.API.service.ProjectCollaboratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
