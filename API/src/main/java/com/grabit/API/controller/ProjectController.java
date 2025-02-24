package com.grabit.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.model.Task;
import com.grabit.API.service.ProjectCollaboratorService;
import com.grabit.API.service.ProjectService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectCollaboratorService projectCollaboratorService) {
        this.projectService = projectService;
        this.projectCollaboratorService = projectCollaboratorService;
    }

    // Create or update a project
  @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project, @RequestBody ProjectCollaboratorModel projectCollaborator) {
        Project savedProject = projectService.createProject(project);
        projectCollaborator.setProject(savedProject);
        projectCollaborator.setRoleID(1);
        projectCollaborator.setJoinedAt(LocalDateTime.now());
        projectCollaborator.setIsActive((byte) 1);
        projectCollaboratorService.addProjectCollaborator(projectCollaborator);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // Get a project by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // close a project by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeProject(@PathVariable Integer id) {
        projectService.closeProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getProjectTasks(@PathVariable Integer id) {
        return projectService.getProjectTasksByProjectId(id);
    }

    @GetMapping("/{id}/collaborators")
    public ResponseEntity<List<ProjectCollaboratorModel>> getProjectCollaborators(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectId(id));
    }

    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<Object> getProjectLeaderboard(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectId(id));
    }

    // update a project by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        return ResponseEntity.ok(updatedProject);
    }

}
