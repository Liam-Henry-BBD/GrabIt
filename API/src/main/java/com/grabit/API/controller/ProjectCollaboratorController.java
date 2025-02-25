// contain classes that handle HTTP requests and responses

package com.grabit.API.controller;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.service.ProjectCollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {
    // Endpoints for project collaborators
    // 1. Get all project collaborators
    // 2. Get project collaborator by ID
    // 3. Add a new project collaborator
    // 4. Update project collaborator
    // 5. Deactivate project collaborator

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
