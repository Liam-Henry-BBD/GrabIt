package com.grabit.app.service;

import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectCreationDTO;
import com.grabit.app.dto.ProjectLeaderboardDTO;
import com.grabit.app.enums.Roles;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectService extends Task {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;
    private final Map<String, String> responseMessages;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
            TaskCollaboratorRepository taskCollaboratorRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
        this.responseMessages = new HashMap<>();
        this.responseMessages.put("404", "Project not found");
        this.responseMessages.put("noTasks", "No tasks have been completed");
    }

    @Transactional
    public void createProject(ProjectCreationDTO request, User user) {
        Project project = new Project();
        project.setProjectName(request.getProjectName());
        project.setProjectDescription(request.getProjectDescription());
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        project.setActive(true);
        Project newProject = projectRepository.save(project);
        projectCollaboratorRepository.insertCollaborator(LocalDateTime.now(), user.getUserID(),
                Roles.PROJECT_LEAD.getRole(), newProject.getProjectID());

    }

    public boolean isProjectCollaborator(Integer userID, Integer projectID) {
        if (userID == null) {
            return false;
        }
        return projectCollaboratorRepository.existsByUserIDAndProjectID(userID, projectID);
    }

    public boolean isProjectLead(User user, Integer projectID) {

        if (user == null) {
            return false;
        }

        return projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(), projectID,
                Roles.PROJECT_LEAD.getRole());
    }

    public List<ProjectAndRoleDTO> getAllProjects(User user, Boolean active) {

        return projectRepository.getProjectsByUserID(user.getUserID(), active);
    }

    public Project getProjectByID(Integer projectID) {
        return projectRepository.findById(projectID).orElseThrow(() -> new NotFound(responseMessages.get("404")));
    }

    @Transactional
    public void closeProject(Integer projectID) {
        projectRepository.findById(projectID).orElseThrow(() -> new NotFound(responseMessages.get("404")));
        projectRepository.deactivateProject(projectID);
    }

    public List<Task> getProjectTasksByProjectID(Integer projectID) {
        return taskRepository.findByProjectID(projectID);
    }

    public List<Task> getProjectTasksByProjectIDAndUserID(Integer projectID, Integer userId) {
        return taskRepository.findByProjectIDAndUserID(projectID, userId);
    }

    public List<ProjectLeaderboardDTO> getProjectLeaderboardByProjectID(Integer projectID) {
        String[][] results = projectRepository.getProjectLeaderboard(projectID);

        if (results.length == 0) {
            throw new BadRequest(responseMessages.get("noTasks"));
        }

        List<ProjectLeaderboardDTO> leaderboard = Arrays.stream(results)
                .map(row -> new ProjectLeaderboardDTO(null, Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2])))
                .sorted((a, b) -> b.getTotalScore().compareTo(a.getTotalScore()))
                .collect(Collectors.toList());

        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).setPosition(i + 1);
        }

        return leaderboard;
    }

    public List<ProjectCollaborator> getProjectCollaboratorsByProjectID(Integer projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    public Project updateProject(Integer projectID, Project project) {
        Project existingProject = projectRepository.findById(projectID)
                .orElseThrow(() -> new NotFound(responseMessages.get("404")));
        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setUpdatedAt(new Date());
        existingProject.setActive(project.isActive());
        return projectRepository.save(existingProject);
    }

    public boolean isCollaborator(Integer projectID, User user) {
        return isProjectCollaborator(user.getUserID(), projectID);
    }
}
