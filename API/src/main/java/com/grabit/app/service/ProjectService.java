package com.grabit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.app.dto.ProjectLeaderboardDTO;
import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.Task;
import com.grabit.app.repository.ProjectCollaboratorRepository;
import com.grabit.app.repository.ProjectRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.TaskRepository;

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

    public Project createProject(Project project) {
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void closeProject(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(id);
    }

    public List<Task> getProjectTasksByProjectId(Integer projectID) {
        return taskRepository.findByProjectID(projectID);
    }

    public Object getProjectLeaderboardByProjectId(Integer projectId) {
//        TODO: CHOOSE ONE DATATYPE, BE CONSISTENT WITH projectId or projectID....
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

    public List<ProjectCollaborator> getProjectCollaboratorsByProjectId(Integer projectID) {
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

}
