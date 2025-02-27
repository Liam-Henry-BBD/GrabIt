package com.grabit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.repository.ProjectCollaboratorRepository;

import java.util.List;

@Service
public class ProjectCollaboratorService {

    @Autowired
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(int projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    public void addProjectCollaborator(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.findByProjectID(projectCollaborator.getProjectID());
        projectCollaboratorRepository.insertCollaborator(projectCollaborator.getJoinedAt(), projectCollaborator.getUserID(), projectCollaborator.getRoleID(), projectCollaborator.getProjectID());
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