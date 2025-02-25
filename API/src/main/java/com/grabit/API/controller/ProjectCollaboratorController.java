package com.grabit.API.controller;

import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.service.ProjectCollaboratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {

    @Autowired
    private ProjectCollaboratorService projectCollaboratorService;

    @GetMapping("/{id}")
    public ProjectCollaboratorModel getProjectCollaboratorByID(@PathVariable Long id) {
        return projectCollaboratorService.getProjectCollaboratorByID(id);
    }

    @DeleteMapping("/{id}")
    public void deactivateProjectCollaborator(@PathVariable Long id) {
        projectCollaboratorService.deactivateProjectCollaborator(id);
    }
}
