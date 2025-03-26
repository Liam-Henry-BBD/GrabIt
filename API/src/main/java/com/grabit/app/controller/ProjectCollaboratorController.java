package com.grabit.app.controller;

import com.grabit.app.dto.CreateResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.UserService;

@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {

    private final ProjectCollaboratorService projectCollaboratorService;
    private final UserService userService;

    public ProjectCollaboratorController(ProjectCollaboratorService projectCollaboratorService,
            UserService userService) {
        this.projectCollaboratorService = projectCollaboratorService;
        this.userService = userService;
    }

    @GetMapping("/{projectCollabID}")
    public ResponseEntity<ProjectCollaborator> getProjectCollaboratorByID(@PathVariable Integer projectCollabID,
            Authentication authentication) {
        ProjectCollaborator collaborator = projectCollaboratorService.getProjectCollaboratorByID(projectCollabID);
        return ResponseEntity.ok(collaborator);
    }

    @DeleteMapping("/{projectCollabID}")
    public ResponseEntity<Void> deactivateProjectCollaborator(@PathVariable Integer projectCollabID,
            Authentication authentication) {
        projectCollaboratorService.deactivateProjectCollaborator(projectCollabID);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createProjectCollaborator(
            @RequestBody ProjectCollaborator projectCollaborator,
            Authentication authentication) {

        projectCollaboratorService.addProjectCollaborator(projectCollaborator,
                userService.getAuthenticatedUser(authentication));
        CreateResponseDTO responseDTO = new CreateResponseDTO("Successfully added collaborator", 201, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<CreateResponseDTO> createProjectCollaboratorsFromList(
            @RequestBody List<ProjectCollaborator> projectCollaborators,
            Authentication authentication) {
        projectCollaborators.forEach(projectCollaborator -> {
            projectCollaboratorService.addProjectCollaborator(projectCollaborator,
                    userService.getAuthenticatedUser(authentication));
        });

        CreateResponseDTO responseDTO = new CreateResponseDTO("Successfully added collaborators", 201, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
