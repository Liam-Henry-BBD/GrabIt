package com.grabit.API.repository;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaboratorModel, Integer> {

        List<ProjectCollaboratorModel> findByprojectCollaboratorID(Long ProjectCollaboratorID);

        List<ProjectCollaboratorModel> findByIsActiveOrderByJoinedAtDesc(boolean isActive);

        List<ProjectCollaboratorModel> findByIsActive(boolean isActive);

        long countByProject_ProjectID(int projectID);

        long countByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByProject();

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p WHERE pc.isActive = true GROUP BY p.projectID")
        List<Object[]> countActiveCollaboratorsByProject();

        List<ProjectCollaboratorModel> findByProject_ProjectID(int projectID);

        List<ProjectCollaboratorModel> findByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

        long countByProject_ProjectIDAndJoinedAtAfter(int projectID, LocalDateTime date);

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByAllProjects();

        @Query("SELECT pc FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = :projectID ORDER BY pc.joinedAt ASC")
        ProjectCollaboratorModel findFirstCollaboratorForProject(int projectID);

        @Query("SELECT p FROM Project p WHERE NOT EXISTS (SELECT 1 FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = p.projectID)")
        List<Project> findProjectsWithNoCollaborators();

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p WHERE p.projectID IN :projectIDs GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByProjectIDs(@Param("projectIDs") List<Integer> projectIDs);

        List<ProjectCollaboratorModel> findByProject_ProjectIDAndJoinedAtBefore(int projectID, LocalDateTime date);
}
