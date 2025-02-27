package com.grabit.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Integer> {

        List<ProjectCollaborator> findByProjectCollaboratorID(Long ProjectCollaboratorID);

        List<ProjectCollaborator> findByIsActiveOrderByJoinedAtDesc(boolean isActive);

        List<ProjectCollaborator> findByIsActive(boolean isActive);

        long ProjectID(int projectID);

        long countByProjectIDAndIsActive(int projectID, boolean isActive);

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByProject();

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID WHERE pc.isActive = true GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countActiveCollaboratorsByProject();

        List<ProjectCollaborator> findByProjectID(int projectID);

        List<ProjectCollaborator> findByProjectIDAndIsActive(int projectID, boolean isActive);

        long countByProjectIDAndJoinedAtAfter(int projectID, LocalDateTime date);

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByAllProjects();

        @Query(value = "SELECT * FROM ProjectCollaborators pc WHERE pc.projectID = :projectID ORDER BY pc.joinedAt ASC", nativeQuery = true)
        ProjectCollaborator findFirstCollaboratorForProject(int projectID);

        @Query(value = "SELECT * FROM Projects p WHERE NOT EXISTS (SELECT 1 FROM ProjectCollaborators pc WHERE pc.projectID = p.projectID)", nativeQuery = true)
        List<Project> findProjectsWithNoCollaborators();

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID WHERE p.projectID IN :projectIDs GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByProjectIDs(@Param(value = "projectIDs") List<Integer> projectIDs);

        List<ProjectCollaborator> findByProjectIDAndJoinedAtBefore(int projectID, LocalDateTime date);

        @Modifying
        @Query(value = "INSERT INTO ProjectCollaborators(JoinedAt, UserID, RoleID, TaskID) VALUES(:joinedAt, :userID, :roleID, :projectID)", nativeQuery = true)
        void insertCollaborator(LocalDateTime joinedAt, Integer userID, Integer roleID, Integer ProjectID);
}
