package com.grabit.API.service;

import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.repository.ProjectCollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCollaboratorService {

    @Autowired
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    // getting all the project collaborators per specific project
    public List<ProjectCollaboratorModel> getAllProjectCollaboratorsByProjectID(int projectId) {
        return projectCollaboratorRepository.findByProject_ProjectID(projectId);
    }

    public ProjectCollaboratorModel addProjectCollaborator(ProjectCollaboratorModel projectCollaborator) {
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaboratorModel getProjectCollaboratorByID(Long id) {
        return projectCollaboratorRepository.findById(id.intValue()).orElse(null);
    }

    public void deactivateProjectCollaborator(Long id) {
        projectCollaboratorRepository.deleteById(id.intValue());
    }
}