package com.grabit.app.service;

import com.grabit.app.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.app.dto.ProjectAndRoleDTO;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService extends Task {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
            TaskCollaboratorRepository taskCollaboratorRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;

    }

    public Project createProject(Project project, User user) {
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
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

    public List<ProjectAndRoleDTO> getAllProjects(User user) {
        return projectRepository.getProjectsByUserID(user.getUserID());
    }

    public Project getProjectByID(Integer projectID) {
        return projectRepository.findById(projectID).orElseThrow(() -> new NotFound("Project not found"));
    }

    public void closeProject(Integer projectID) {
        projectRepository.findById(projectID).orElseThrow(() -> new NotFound("Project not found"));
        //TODO: Soft deletion, deacticate the project instead?
        projectRepository.deleteById(projectID);
    }

    public List<Task> getProjectTasksByProjectID(Integer projectID) {
        return taskRepository.findByProjectID(projectID);
    }

    public Object getProjectLeaderboardByProjectID(Integer projectID) {
        String[][] results = projectRepository.getProjectLeaderboard(projectID);

        if (results.length == 0) {
            return "No tasks have been completed yet.";
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
                .orElseThrow(() -> new NotFound("Project not found"));
        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setUpdatedAt(new Date());
        return projectRepository.save(existingProject);
    }

    public boolean isCollaborator(Integer projectID, User user) {

        return isProjectCollaborator(user.getUserID(), projectID);
    }
}
