package com.grabit.app.controller;

import com.grabit.app.dto.ProjectLeaderboardDTO;
import com.grabit.app.dto.TaskAndRoleDTO;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.model.ProjectCollaborator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.grabit.app.dto.CreateResponseDTO;
import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;

import com.grabit.app.model.Project;
import com.grabit.app.model.User;
import com.grabit.app.service.ProjectService;
import com.grabit.app.service.UserService;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:8000")
public class ProjectController {
    private final Map<String, String> responseMessages;
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService,
            UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
        this.responseMessages = new HashMap<>();
        this.responseMessages.put("created", "Your project has been created successfully");
        this.responseMessages.put("invalidCollaborator", "You are not a collaborator on this project");
        this.responseMessages.put("invalidRole", "You are not a leader on this project");
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createProject(@RequestBody ProjectCreationDTO request,
            Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        Project newProject = projectService.createProject(request, user);
        return new ResponseEntity<>(
                new CreateResponseDTO(responseMessages.get("created"), 201,
                        newProject != null ? newProject.getProjectID() : null),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectAndRoleDTO>> getAllProjects(Authentication authentication) {
        List<ProjectAndRoleDTO> allUserProjects = projectService
                .getAllProjects(userService.getAuthenticatedUser(authentication));

        return ResponseEntity.ok(allUserProjects);
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<ProjectAndRoleDTO> getProjectByID(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            throw new BadRequest(responseMessages.get("invalidCollaborator"));
        }

        ProjectAndRoleDTO  project = projectService.getProjectByID(projectID, userService.getAuthenticatedUser(authentication));
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<Project> closeProject(@PathVariable Integer projectID,
            Authentication authentication) {
        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        if (!isCollaborator) {
            throw new BadRequest(responseMessages.get("invalidRole"));
        }

        projectService.closeProject(projectID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectID}/tasks")
    public ResponseEntity<List<TaskAndRoleDTO>> getProjectTasks(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            throw new BadRequest(responseMessages.get("invalidCollaborator"));
        }

        List<TaskAndRoleDTO> projectTasksByProjectID = projectService.getTasksAndRole(projectID);
        // List<TaskDTO> dtos = TaskDTO.getTasks(projectTasksByProjectID);
        return ResponseEntity.ok(projectTasksByProjectID);
    }

    @GetMapping("/{projectID}/my-tasks")
    public ResponseEntity<List<TaskAndRoleDTO>> getMyProjectTasks(@PathVariable Integer projectID,
            Authentication authentication) {
        Integer userID = userService.getAuthenticatedUser(authentication).getUserID();

        if (!projectService.isProjectCollaborator(userID, projectID)) {
            throw new BadRequest(responseMessages.get("invalidCollaborator"));
        }
        return ResponseEntity.ok(projectService.getMyProjectTasksByProjectIDAndUserID(projectID, userID));
    }

    @GetMapping("/{projectID}/collaborators")
    public ResponseEntity<List<ProjectCollaborator>> getProjectCollaborators(@PathVariable Integer projectID,
            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            throw new BadRequest(responseMessages.get("invalidCollaborator"));
        }

        return ResponseEntity.ok(projectService.getProjectCollaboratorsByProjectID(projectID));
    }

    @GetMapping("/{projectID}/leaderboard")
    public ResponseEntity<List<ProjectLeaderboardDTO>> getProjectLeaderboard(@PathVariable Integer projectID,

            Authentication authentication) {
        if (!projectService.isProjectCollaborator(userService.getAuthenticatedUser(authentication).getUserID(),
                projectID)) {
            throw new BadRequest(responseMessages.get("invalidCollaborator"));
        }

        return ResponseEntity.ok(projectService.getProjectLeaderboardByProjectID(projectID));
    }

    @PutMapping("/{projectID}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectID, @RequestBody Project project,
            Authentication authentication) {
        boolean isCollaborator = projectService.isProjectLead(userService.getAuthenticatedUser(authentication),
                projectID);

        if (!isCollaborator) {
            throw new BadRequest(responseMessages.get("invalidRole"));
        }

        Project updatedProject = projectService.updateProject(projectID, project);
        return ResponseEntity.ok(updatedProject);
    }
}
