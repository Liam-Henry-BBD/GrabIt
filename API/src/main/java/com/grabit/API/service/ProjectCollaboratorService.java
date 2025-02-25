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

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(int projectId) {
        return projectCollaboratorRepository.findByProjectID(projectId);
    }

    public ProjectCollaborator addProjectCollaborator(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.findByProjectID(projectCollaborator.getProjectID());
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaborator getProjectCollaboratorByID(Long id) {
        return projectCollaboratorRepository.findById(id.intValue()).orElse(null);
    }

    public void deactivateProjectCollaborator(Long id) {
        projectCollaboratorRepository.deleteById(id.intValue());
    }

    public List<ProjectCollaborator> getAllActiveProjectCollaborators() {
        return projectCollaboratorRepository.findByIsActive(true);
    }
}