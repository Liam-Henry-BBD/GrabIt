package com.grabit.app.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.dto.TaskDTO;

import com.grabit.app.model.Project;
import com.grabit.app.model.User;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService,
            UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationDTO request,
            Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Project newProject = projectService.createProject(request, user);
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectAndRoleDTO>> getAllProjects(Authentication authentication) {
        List<ProjectAndRoleDTO> allUserProjects = projectService
                .getAllProjects(userService.getAuthenticatedUser(authentication));

        return ResponseEntity.ok(allUserProjects);
    }

    @GetMapping("/{projectID}")

    public ResponseEntity<Object> getProjectByID(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You do not have permission to view this project.");
        }

        Project project = projectService.getProjectByID(projectID);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<Object> closeProject(@PathVariable Integer projectID,
            Authentication authentication) {
        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        Optional<Project> projectOptional = Optional.ofNullable(projectService.getProjectByID(projectID));

        if (!projectOptional.isPresent()) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "NOT_FOUND");
            jsonResponse.put("message", "Project does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }

        if (!isCollaborator) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You do not have permission to close this project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jsonResponse);
        }

        projectService.closeProject(projectID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectID}/tasks")
    public ResponseEntity<Object> getProjectTasks(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You are not a collaborator on this project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jsonResponse);
        }

        return ResponseEntity.ok(projectService.getProjectTasksByProjectID(projectID));
    }

    @GetMapping("/{projectID}/my-tasks")
    public ResponseEntity<List<TaskDTO>> getMyProjectTasks(@PathVariable Integer projectID,
            Authentication authentication) {
        Integer userID = userService.getAuthenticatedUser(authentication).getUserID();

        if (!projectService.isProjectCollaborator(userID, projectID)) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You are not a collaborator on this project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(projectService.getProjectTasksByProjectIDAndUserID(projectID, userID));
    }

    @GetMapping("/{projectID}/collaborators")

    public ResponseEntity<Object> getProjectCollaborators(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You do not have permission to view the project collaborators.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jsonResponse);
        }
        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectID(projectID));
    }

    @GetMapping("/{projectID}/leaderboard")
    public ResponseEntity<Object> getProjectLeaderboard(@PathVariable Integer projectID,

            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You do not have permission to view the project leaderboard.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jsonResponse);
        }

        return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectID(projectID));
    }

    @PutMapping("/{projectID}")
    public ResponseEntity<Object> updateProject(@PathVariable Integer projectID, @RequestBody Project project,
            Authentication authentication) {
        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        if (!isCollaborator) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "FORBIDDEN");
            jsonResponse.put("message", "You do not have permission to update this project.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(jsonResponse);
        }

        Project updatedProject = projectService.updateProject(projectID, project);
        return ResponseEntity.ok(updatedProject);
    }
}
