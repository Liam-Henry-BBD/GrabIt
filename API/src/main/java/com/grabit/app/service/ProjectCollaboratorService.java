package com.grabit.app.service;

import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.Conflict;
import org.springframework.stereotype.Service;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ProjectCollaboratorService {


    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    public ProjectCollaboratorService(ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectCollaboratorRepository = projectCollaboratorRepository;
    }

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(Integer projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    @Transactional
    public void addProjectCollaborator(ProjectCollaborator projectCollaborator, User user) {
        boolean isDuplicate = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(
                projectCollaborator.getUserID(),
                projectCollaborator.getProjectID(),
                projectCollaborator.getRoleID());
        if (isDuplicate) {
            throw new Conflict("User already a collaborator.");
        }

        boolean userExists = projectCollaboratorRepository.existsByUserIDAndProjectID(user.getUserID(), projectCollaborator.getProjectID());
    
        if (userExists) {
            throw new BadRequest("User is already a collaborator for this project.");
        }
    
        projectCollaboratorRepository.insertCollaborator(projectCollaborator.getJoinedAt(),
                user.getUserID(), projectCollaborator.getRoleID(), projectCollaborator.getProjectID());
    }
    

    public ProjectCollaborator getProjectCollaboratorByID(Integer id) {
        return projectCollaboratorRepository.findById(id.intValue()).orElse(null);
    }

    public void deactivateProjectCollaborator(Integer id) {
        projectCollaboratorRepository.deleteById(id.intValue());
    }

    public List<ProjectCollaborator> getAllActiveProjectCollaborators() {
        return projectCollaboratorRepository.findByIsActive(true);
    }

    public boolean exists(Integer userID, Integer projectID) {
        return projectCollaboratorRepository.existsByUserIDAndProjectID(userID, projectID);
    }

}