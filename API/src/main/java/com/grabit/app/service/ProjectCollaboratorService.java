package com.grabit.app.service;

import com.grabit.app.enums.Roles;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.model.Role;
import com.grabit.app.repository.RoleRepository;
import org.springframework.stereotype.Service;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectCollaboratorService {

    private final ProjectService projectService;

    private final ProjectCollaboratorRepository projectCollaboratorRepository;
    private final RoleRepository roleRepository;

    public ProjectCollaboratorService(ProjectService projectService,
                                      ProjectCollaboratorRepository projectCollaboratorRepository, RoleRepository roleRepository) {
        this.projectService = projectService;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
        this.roleRepository = roleRepository;
    }

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(Integer projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    @Transactional
    public void addProjectCollaborator(ProjectCollaborator projectCollaborator, User user) {

        boolean userExists = projectCollaboratorRepository.existsByUserIDAndProjectID(projectCollaborator.getUserID(),
                projectCollaborator.getProjectID());


        Role role = roleRepository.findById(Integer.valueOf(projectCollaborator.getRoleID()))
                .orElseThrow(() -> new NotFound("Role not found"));

            if (!role.getRoleID().equals(Roles.PROJECT_MEMBER.getRole())) {
            throw new BadRequest("Collaborators can only be added as a project member");
        }

        if (userExists) {
            throw new BadRequest("User is already a collaborator for this project.");
        }

        boolean isProjectLead = projectService.isProjectLead(user, projectCollaborator.getProjectID());
        if (!isProjectLead) {
            throw new BadRequest("Only project leaders can add project collaborators.");
        }

        projectCollaboratorRepository.insertCollaborator(LocalDateTime.now(),
                projectCollaborator.getUserID(), projectCollaborator.getRoleID(), projectCollaborator.getProjectID());
    }

    @Transactional
    public void addProjectLead(ProjectCollaborator projectCollaborator, User user) {


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

        if (projectCollaboratorRepository.existsByUserIDAndProjectID(projectCollaborator.getUserID(),
                projectCollaborator.getProjectID())) {
            throw new BadRequest("User is already a collaborator for this project.");
        }

        if(projectService.isProjectLead(null, null)) {
            throw new BadRequest("Only project leaders can add project collaborators.");
        }
            
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
