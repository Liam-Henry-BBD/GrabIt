package com.grabit.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.API.model.Project;
import com.grabit.API.model.Task;
import com.grabit.API.repository.ProjectRepository;
import com.grabit.API.repository.TaskRepository;

import java.util.List;

@Service
public class ProjectService extends Task {

    // private TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    // Save a new project or update an existing one
    public Project saveProject(Project project) {
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
    public void deleteProject(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(id);
    }

    public List<Task> getProjectTasksByProjectId(Integer projectID) {
        return taskRepository.findByProject_ProjectID(projectID);
    }
}
