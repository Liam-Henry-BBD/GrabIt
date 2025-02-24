package com.grabit.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.API.dataTransferObject.ProjectLeaderboardDTO;
import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.model.Task;
import com.grabit.API.repository.ProjectCollaboratorRepository;
import com.grabit.API.repository.ProjectRepository;
import com.grabit.API.repository.TaskCollaboratorRepository;
import com.grabit.API.repository.TaskRepository;

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
            TaskCollaboratorRepository taskCollaboratorsRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
    }

    // Save a new project
    public Project createProject(Project project) {
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get a project by its ID
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    // Delete a project by ID
    public void closeProject(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(id);
    }

    // Get all tasks for a specific project ID
    public List<Task> getProjectTasksByProjectId(Integer projectID) {
        return taskRepository.findByProject_ProjectID(projectID);
    }

    // Get the leaderboard for a specific project ID
    public Object getProjectLeaderboardByProjectId(Integer projectId) {
        List<Object[]> results = projectRepository.getProjectLeaderboard(projectId);

        if (results.isEmpty()) {
            return "No tasks have been completed yet.";
        }

        List<ProjectLeaderboardDTO> leaderboard = results.stream()
                .map(row -> new ProjectLeaderboardDTO(null, (Integer) row[0], (String) row[1], (Integer) row[2]))
                .sorted((a, b) -> b.getTotalScore().compareTo(a.getTotalScore()))
                .collect(Collectors.toList());

        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).setPosition(i + 1);
        }

        return leaderboard;
    }

    // Get all collaborators for a specific project ID
    public List<ProjectCollaboratorModel> getProjectCollaboratorsByProjectId(Integer projectID) {
        return projectCollaboratorRepository.findByProject_ProjectID(projectID);
    }

    public Project updateProject(Integer id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setUpdatedAt(new Date());
        return projectRepository.save(existingProject);
    }

}
