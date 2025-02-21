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

    public List<ProjectCollaboratorModel> getAllProjectCollaborators() {
        return projectCollaboratorRepository.findAll();
    }

    public ProjectCollaboratorModel addProjectCollaborator(ProjectCollaboratorModel projectCollaborator) {
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaboratorModel getProjectCollaboratorByID(Long id) {
        return projectCollaboratorRepository.findById(id.intValue()).orElse(null);
    }

    public ProjectCollaboratorModel updateProjectCollaborator(Long id, ProjectCollaboratorModel projectCollaborator) {
        projectCollaborator.setProjectCollaboratorID(id.intValue());
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public void deleteProjectCollaborator(Long id) {
        projectCollaboratorRepository.deleteById(id.intValue());
    }
}