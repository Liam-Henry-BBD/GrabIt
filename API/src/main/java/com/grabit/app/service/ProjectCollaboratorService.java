package com.grabit.app.service;

import com.grabit.app.exceptions.BadRequest;
import org.springframework.stereotype.Service;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ProjectCollaboratorService {

    private final ProjectService projectService;

    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    public ProjectCollaboratorService(ProjectService projectService,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectService = projectService;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
    }

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(Integer projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    @Transactional
    public void addProjectCollaborator(ProjectCollaborator projectCollaborator, User user) {

        boolean userExists = projectCollaboratorRepository.existsByUserIDAndProjectID(user.getUserID(),
                projectCollaborator.getProjectID());

        if (userExists) {
            throw new BadRequest("User is already a collaborator for this project.");
        }

        boolean isProjectLead = projectService.isProjectLead(user, projectCollaborator.getProjectID());
        if (!isProjectLead) {
            throw new BadRequest("Only project leaders can add project collaborators.");
        }

        projectCollaboratorRepository.insertCollaborator(projectCollaborator.getJoinedAt(),
                projectCollaborator.getUserID(), projectCollaborator.getRoleID(), projectCollaborator.getProjectID());
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

    public ProjectCollaborator updateProjectCollaborator(Integer id, ProjectCollaborator projectCollaboratorDetails) {
        ProjectCollaborator projectCollaborator = projectCollaboratorRepository.findById(id.intValue()).orElse(null);

        if (projectCollaborator == null) {
            return null;
        }

        if (projectCollaboratorDetails.getUserID() != null) {
            projectCollaborator.setRoleID(projectCollaboratorDetails.getRoleID());
        }

        if (projectCollaboratorDetails.getRoleID() != null) {
            projectCollaborator.setRoleID(projectCollaboratorDetails.getRoleID());
        }

        if (projectCollaboratorDetails.getProjectID() != null) {
            projectCollaborator.setProjectID(projectCollaboratorDetails.getProjectID());
        }

        if (projectCollaboratorDetails.getJoinedAt() != null) {
            projectCollaborator.setJoinedAt(projectCollaboratorDetails.getJoinedAt());
        }

        projectCollaborator.setRoleID(projectCollaboratorDetails.getRoleID());
        projectCollaboratorRepository.save(projectCollaborator);
        return projectCollaborator;
    }

    public List<ProjectCollaborator> getAllProjectCollaborators() {
        return projectCollaboratorRepository.findAll();
    }

    public void putProjectCollaborator(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.save(projectCollaborator);

    }

    @Transactional
    public ProjectCollaborator putProjectCollaborator(Integer id, ProjectCollaborator projectCollaboratorDetails) {
        ProjectCollaborator existingCollaborator = projectCollaboratorRepository.findById(id).orElse(null);

        if (existingCollaborator == null) {
            return null;
        }

        if (projectCollaboratorDetails.getUserID() != null) {
            existingCollaborator.setUserID(projectCollaboratorDetails.getUserID());
        }

        if (projectCollaboratorDetails.getRoleID() != null) {
            existingCollaborator.setRoleID(projectCollaboratorDetails.getRoleID());
        }

        if (projectCollaboratorDetails.getProjectID() != null) {
            existingCollaborator.setProjectID(projectCollaboratorDetails.getProjectID());
        }

        if (projectCollaboratorDetails.getJoinedAt() != null) {
            existingCollaborator.setJoinedAt(projectCollaboratorDetails.getJoinedAt());
        }

        existingCollaborator.setUserID(projectCollaboratorDetails.getUserID());
        existingCollaborator.setRoleID(projectCollaboratorDetails.getRoleID());
        existingCollaborator.setProjectID(projectCollaboratorDetails.getProjectID());
        existingCollaborator.setJoinedAt(projectCollaboratorDetails.getJoinedAt());

        return projectCollaboratorRepository.save(existingCollaborator);
    }
}
