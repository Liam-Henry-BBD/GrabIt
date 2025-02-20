package com.grabit.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.API.model.Project;
import com.grabit.API.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    // Delete a project by ID
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }
}
