package com.grabit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.dto.ProjectAndRoleDTO;
import com.grabit.app.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = """
            SELECT
                u.UserID,
                u.GitHubID,
                SUM(COALESCE(tp.TaskPointID, 0)) AS TotalScore
            FROM
                [grabit].[Users] u
            JOIN
                [grabit].[TaskCollaborators] tc ON u.UserID = tc.UserID
            JOIN
                [grabit].[Tasks] t ON t.TaskID = tc.TaskID
            LEFT JOIN
                [grabit].[TaskPoints] tp ON tp.TaskPointID = t.TaskPointID
            WHERE
                tc.isActive = 1
                AND t.TaskStatusID = 4
                AND t.ProjectID = :projectID
            GROUP BY
                u.UserID, u.GitHubID
            ORDER BY
                TotalScore DESC;
                        """, nativeQuery = true)
    String[][] getProjectLeaderboard(@Param("projectID") Integer projectID);

    @Query(value = """
            SELECT
                p.ProjectID,
                p.ProjectName,
                p.ProjectDescription,
                pc.ProjectCollaboratorID,
                pc.RoleID AS CollaboratorRole,
                p.isActive
            FROM
                [grabit].Projects p
            LEFT JOIN
                [grabit].ProjectCollaborators pc ON p.ProjectID = pc.ProjectID
            WHERE
                pc.UserID = :userID
            AND 
                p.isActive = :isActive;
            """, nativeQuery = true)
    List<ProjectAndRoleDTO>  getProjectsByUserID(@Param("userID") Integer userID,Boolean isActive);

    @Modifying
    @Query(value = "UPDATE Project SET isActive = false WHERE projectID = :projectID")
    void deactivateProject(@Param("projectID") Integer projectID);
    
}
