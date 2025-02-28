package com.grabit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.dto.ProjectLeaderboardDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.TaskRepository;
import com.grabit.app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService extends Task {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;
    private final UserRepository userRepository;
    private final GitHubService gitHubService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
                          TaskCollaboratorRepository taskCollaboratorRepository,
                          ProjectCollaboratorRepository projectCollaboratorRepository,
                          UserRepository userRepository, GitHubService gitHubService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
        this.userRepository = userRepository;
        this.gitHubService = gitHubService;
    }

    public Project createProject(Project project) {
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
    }

    public boolean isProjectCollaborator(String githubToken, Integer projectID) {
        User user = userRepository.findByGitHubID(gitHubService.getGitHubUserLogin(githubToken));

        if (user == null) {
            return false;
        }

        return projectCollaboratorRepository.existsByUserIDAndProjectID(user.getUserID(), projectID);
    }

    public boolean isProjectLead(String githubToken, Integer projectID) {
        User user = userRepository.findByGitHubID(gitHubService.getGitHubUserLogin(githubToken));

        if (user == null) {
            return false;
        }

        return projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(projectID, user.getUserID(), 1);
    }

    public List<ProjectAndRoleDTO> getAllProjects(@AuthenticationPrincipal OAuth2User user, HttpSession httpSession) {
        String githubToken = (String) httpSession.getAttribute("github_access_token");
        String githubLogin = gitHubService.getGitHubUserLogin(githubToken);
        User currentUser = userRepository.findByGitHubID(githubLogin);

        return projectRepository.getProjectsByUserID(currentUser.getUserID());
    }

    public Project getProjectByID(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void closeProject(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(id);
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

    public Project updateProject(Integer id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setUpdatedAt(new Date());
        return projectRepository.save(existingProject);
    }

    public boolean isCollaborator(Integer projectID, HttpSession httpSession) {
        String githubToken = (String) httpSession.getAttribute("github_access_token");

        return isProjectCollaborator(githubToken, projectID);
    }
}
