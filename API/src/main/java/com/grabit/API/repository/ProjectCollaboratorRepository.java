package com.grabit.API.repository;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaborator;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Integer> {

        List<ProjectCollaborator> findByprojectCollaboratorID(Long ProjectCollaboratorID);

        List<ProjectCollaborator> findByIsActiveOrderByJoinedAtDesc(boolean isActive);

        List<ProjectCollaborator> findByIsActive(boolean isActive);

        long countByProject_ProjectID(int projectID);

        long countByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByProject();

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p WHERE pc.isActive = true GROUP BY p.projectID")
        List<Object[]> countActiveCollaboratorsByProject();

        List<ProjectCollaborator> findByProject_ProjectID(int projectID);

        List<ProjectCollaborator> findByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

        long countByProject_ProjectIDAndJoinedAtAfter(int projectID, LocalDateTime date);

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByAllProjects();

        @Query("SELECT pc FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = :projectID ORDER BY pc.joinedAt ASC")
        ProjectCollaborator findFirstCollaboratorForProject(int projectID);

        @Query("SELECT p FROM Project p WHERE NOT EXISTS (SELECT 1 FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = p.projectID)")
        List<Project> findProjectsWithNoCollaborators();

        @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "
                        + "JOIN pc.project p WHERE p.projectID IN :projectIDs GROUP BY p.projectID")
        List<Object[]> countCollaboratorsByProjectIDs(@Param("projectIDs") List<Integer> projectIDs);

        List<ProjectCollaborator> findByProject_ProjectIDAndJoinedAtBefore(int projectID, LocalDateTime date);
}
