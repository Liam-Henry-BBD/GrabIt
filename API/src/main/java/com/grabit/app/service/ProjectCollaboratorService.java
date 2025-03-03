package com.grabit.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.app.model.ProjectCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.repository.ProjectCollaboratorRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ProjectCollaboratorService {

    @Autowired
    private ProjectCollaboratorRepository projectCollaboratorRepository;

    public List<ProjectCollaborator> getAllProjectCollaboratorsByProjectID(Integer projectID) {
        return projectCollaboratorRepository.findByProjectID(projectID);
    }

    @Transactional
    public void addProjectCollaborator(ProjectCollaborator projectCollaborator, User user) {
        projectCollaboratorRepository.findByProjectID(projectCollaborator.getProjectID());
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

    public boolean exists(Integer userID, Integer projectID, Byte roleID) {
        return projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(userID,
                projectID, roleID);
    }

}