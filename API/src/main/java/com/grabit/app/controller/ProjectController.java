package com.grabit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.enums.Roles;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;
    private final UserService userService;

    public ProjectController(ProjectService projectService,
                             ProjectCollaboratorService projectCollaboratorService,
                             UserService userService) {
        this.projectService = projectService;
        this.projectCollaboratorService = projectCollaboratorService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationDTO request,
            Authentication authentication) {
        //TODO: Move the logic into the service
        Project project = request.getProject();
        ProjectCollaborator projectCollaborator = request.getProjectCollaborator();

        Project savedProject = projectService.createProject(project, userService.getAuthenticatedUser(authentication));
        //TODO: You do not need any details from the client about project collaborator when creating a project
        projectCollaborator.setProjectID(savedProject.getProjectID());
        projectCollaborator.setRoleID(Roles.PROJECT_LEAD.getRole());
        projectCollaborator.setJoinedAt(LocalDateTime.now());
        projectCollaborator.setActive(true);
        projectCollaboratorService.addProjectCollaborator(projectCollaborator,
                userService.getAuthenticatedUser(authentication));

        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectAndRoleDTO>> getAllProjects(Authentication authentication) {
        List<ProjectAndRoleDTO> allUserProjects = projectService
                .getAllProjects(userService.getAuthenticatedUser(authentication));

        return ResponseEntity.ok(allUserProjects);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<Project> getProjectByID(@PathVariable Integer projectID,
            Authentication authentication) {

        if (!projectService.isCollaborator(projectID, userService.getAuthenticatedUser(authentication))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Project project = projectService.getProjectByID(projectID);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<Void> closeProject(@PathVariable Integer projectID,
            Authentication authentication) {

        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        if (!isCollaborator) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        projectService.closeProject(projectID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectID}/tasks")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable Integer projectID,
            Authentication authentication) {

        if (!projectService.isCollaborator(projectID, userService.getAuthenticatedUser(authentication))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectTasksByProjectID(projectID));
    }

    @GetMapping("/{projectID}/collaborators")
    public ResponseEntity<List<ProjectCollaborator>> getProjectCollaborators(@PathVariable Integer projectID,
            Authentication authentication) {

        if (!projectService.isCollaborator(projectID, userService.getAuthenticatedUser(authentication))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectID(projectID));
    }

    @GetMapping("/{projectID}/leaderboard")
    public ResponseEntity<Object> getProjectLeaderboard(@PathVariable Integer projectID,
            Authentication authentication) {

        if (!projectService.isCollaborator(projectID, userService.getAuthenticatedUser(authentication))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectID(projectID));
    }

    @PutMapping("/{projectID}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectID, @RequestBody Project project,
            Authentication authentication) {

        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        if (!isCollaborator) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Project updatedProject = projectService.updateProject(projectID, project);
        return ResponseEntity.ok(updatedProject);
    }

}
