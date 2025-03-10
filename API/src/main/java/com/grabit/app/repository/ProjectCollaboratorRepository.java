package com.grabit.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.grabit.app.model.TaskCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Integer> {

        List<ProjectCollaborator> findByProjectCollaboratorID(Integer ProjectCollaboratorID);

        List<ProjectCollaborator> findByIsActiveOrderByJoinedAtDesc(boolean isActive);

        List<ProjectCollaborator> findByIsActive(boolean isActive);

        Integer ProjectID(Integer projectID);

        @Query("SELECT pc FROM ProjectCollaborator pc WHERE pc.userID = :userID AND pc.projectID = :projectID")
        ProjectCollaborator findByProjectIDAndUserID(Integer projectID, Integer userID);

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByProject();

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID WHERE pc.isActive = true GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countActiveCollaboratorsByProject();

        List<ProjectCollaborator> findByProjectID(Integer projectID);

        List<ProjectCollaborator> findByProjectIDAndIsActive(Integer projectID, boolean isActive);


        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByAllProjects();

        @Query(value = "SELECT * FROM ProjectCollaborators pc WHERE pc.projectID = :projectID ORDER BY pc.joinedAt ASC", nativeQuery = true)
        ProjectCollaborator findFirstCollaboratorForProject(Integer projectID);

        @Query(value = "SELECT * FROM Projects p WHERE NOT EXISTS (SELECT 1 FROM ProjectCollaborators pc WHERE pc.projectID = p.projectID)", nativeQuery = true)
        List<Project> findProjectsWithNoCollaborators();

        @Query(value = "SELECT p.projectID, COUNT(pc.projectID) FROM  Projects p JOIN ProjectCollaborators pc on pc.projectID = p.projectID WHERE p.projectID IN :projectIDs GROUP BY p.projectID", nativeQuery = true)
        List<Object[]> countCollaboratorsByProjectIDs(@Param(value = "projectIDs") List<Integer> projectIDs);

        @Modifying
        @Query(value = "INSERT INTO ProjectCollaborators(JoinedAt, UserID, RoleID, ProjectID) VALUES(:joinedAt, :userID, :roleID, :projectID)", nativeQuery = true)
        void insertCollaborator(LocalDateTime joinedAt, Integer userID, Byte roleID, Integer projectID);

        @Query("SELECT CASE WHEN COUNT(pc) > 0 THEN true ELSE false END FROM ProjectCollaborator pc WHERE pc.userID = :userID AND pc.projectID = :projectID AND pc.roleID = :roleID")
        boolean existsByUserIDAndProjectIDAndRoleID(@Param("userID") Integer userID,
                        @Param("projectID") Integer projectID,
                        @Param("roleID") Byte roleID);

        @Query("SELECT CASE WHEN COUNT(pc) > 0 THEN true ELSE false END FROM ProjectCollaborator pc WHERE pc.userID = :userID AND pc.projectID = :projectID ")
        boolean existsByUserIDAndProjectID(@Param("userID") Integer userID, @Param("projectID") Integer projectID);


        @Modifying
        @Query(value = "UPDATE ProjectCollaborators SET IsActive = :isActive WHERE ProjectCollaboratorID = :projectCollaboratorID", nativeQuery = true)
        void updateIsActiveByProjectCollaboratorID(@Param("isActive") boolean isActive, @Param("projectCollaboratorID") Integer projectCollaboratorID);

        @Modifying
        @Query(value = "UPDATE ProjectCollaborators SET IsActive = :isActive WHERE ProjectID = :projectID", nativeQuery = true)
        void updateIsActiveByProjectID(@Param("isActive") boolean isActive, @Param("projectID") Integer projectID);

        @Modifying
        @Query(value = "UPDATE ProjectCollaborators SET IsActive = :isActive WHERE ProjectID = :projectID AND UserID = :userID", nativeQuery = true)
        void updateIsActiveByProjectIDAndUserID(@Param("isActive") boolean isActive, @Param("projectID") Integer projectID, @Param("userID") Integer userID);

        @Modifying
        @Query(value = "UPDATE ProjectCollaborators SET IsActive = :isActive WHERE ProjectID = :projectID AND RoleID = :roleID", nativeQuery = true)
        void updateIsActiveByProjectIDAndRoleID(@Param("isActive") boolean isActive, @Param("projectID") Integer projectID, @Param("roleID") Byte roleID);


}
