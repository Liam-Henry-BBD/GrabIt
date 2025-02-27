package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
