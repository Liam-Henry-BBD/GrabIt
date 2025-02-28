package com.grabit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.service.ProjectCollaboratorService;
import com.grabit.app.service.ProjectService;

import jakarta.servlet.http.HttpSession;

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

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationDTO request,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {

        if (httpSession.getAttribute("github_access_token") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Project project = request.getProject();
        ProjectCollaborator projectCollaborator = request.getProjectCollaborator();

        Project savedProject = projectService.createProject(project);
        projectCollaborator.setProjectID(savedProject.getProjectID());
        projectCollaborator.setRoleID(1);
        projectCollaborator.setJoinedAt(LocalDateTime.now());
        projectCollaborator.setActive(true);
        projectCollaboratorService.addProjectCollaborator(projectCollaborator);

        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectAndRoleDTO>> getAllProjects(@AuthenticationPrincipal OAuth2User user,
            HttpSession httpSession) {
        List<ProjectAndRoleDTO> allUserProjects = projectService.getAllProjects(user, httpSession);
        System.out.println(allUserProjects);
        if (allUserProjects.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(allUserProjects);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<Project> getProjectByID(@PathVariable Integer projectID,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {

        if (!projectService.isCollaborator(projectID, httpSession)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Project project = projectService.getProjectByID(projectID);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<Void> closeProject(@PathVariable Integer projectID,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {
        String githubToken = (String) httpSession.getAttribute("github_access_token");

        boolean isCollaborator = projectService.isProjectLead(githubToken, projectID);

        if (!isCollaborator) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        projectService.closeProject(projectID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectID}/tasks")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable Integer projectID,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {

        if (!projectService.isCollaborator(projectID, httpSession)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectTasksByProjectID(projectID));
    }

    @GetMapping("/{projectID}/collaborators")
    public ResponseEntity<List<ProjectCollaborator>> getProjectCollaborators(@PathVariable Integer projectID,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {

        if (!projectService.isCollaborator(projectID, httpSession)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectID(projectID));
    }

    @GetMapping("/{projectID}/leaderboard")
    public ResponseEntity<Object> getProjectLeaderboard(@PathVariable Integer projectID,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {

        if (!projectService.isCollaborator(projectID, httpSession)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectID(projectID));
    }

    @PutMapping("/{projectID}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectID, @RequestBody Project project,
            @AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {
        String githubToken = (String) httpSession.getAttribute("github_access_token");

        boolean isCollaborator = projectService.isProjectLead(githubToken, projectID);

        if (!isCollaborator) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Project updatedProject = projectService.updateProject(projectID, project);
        return ResponseEntity.ok(updatedProject);
    }

}
