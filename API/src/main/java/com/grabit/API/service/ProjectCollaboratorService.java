package com.grabit.API.service;

import com.grabit.API.model.ProjectCollaborator;
import com.grabit.API.repository.ProjectCollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCollaboratorService {

    @Autowired
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    public List<ProjectCollaborator> getAllProjectCollaborators() {
        return projectCollaboratorRepository.findAll();
    }

    public ProjectCollaborator addProjectCollaborator(ProjectCollaborator projectCollaborator) {
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaborator getProjectCollaboratorByID(Long id) {
        return projectCollaboratorRepository.findById(id).orElse(null);
    }

    public ProjectCollaborator updateProjectCollaborator(Long id, ProjectCollaborator projectCollaborator) {
        projectCollaborator.setProjectCollaboratorID(id);
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public void deleteProjectCollaborator(Long id) {
        projectCollaboratorRepository.deleteById(id);
    }
}