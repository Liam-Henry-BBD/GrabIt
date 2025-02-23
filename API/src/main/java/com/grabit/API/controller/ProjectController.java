package com.grabit.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.model.Task;
import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Create or update a project
//    @PostMapping
//    public ResponseEntity<Project> createProject(@RequestBody Project project) {
//        Project savedProject = projectService.saveProject(project);
//        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
//    }

    // Get all projects
//    @GetMapping
//    public ResponseEntity<List<Project>> getAllProjects() {
//        return ResponseEntity.ok(projectService.getAllProjects());
//    }

    // Get a project by its ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
//        return ResponseEntity.ok(projectService.getProjectById(id));
//    }

    // close a project by its ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> closeProject(@PathVariable Integer id) {
//        projectService.closeProject(id);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping("/{id}/tasks")
//    public List<Task> getProjectTasks(@PathVariable Integer id) {
//        return projectService.getProjectTasksByProjectId(id);
//    }

//    @GetMapping("/{id}/collaborators")
//    public ResponseEntity<List<ProjectCollaboratorModel>> getProjectCollaborators(@PathVariable Integer id) {
//        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectId(id));
//    }

    // @GetMapping("/{id}/leaderboard")
    // public ResponseEntity<List<TaskCollaborator>> getProjectLeaderboard(@PathVariable Integer id) {
    //     return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectId(id));
    // }

    // public List<TaskCollaborator> getProjectLeaderboard(@PathVariable Integer id) {
    //     return projectService.getProjectLeaderboardByProjectId(id);
    // }
}
