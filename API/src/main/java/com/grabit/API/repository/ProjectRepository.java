package com.grabit.API.repository;

import com.grabit.API.model.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = """
            SELECT u.UserID, u.GitHubID, SUM(COALESCE(tp.TaskPointID, 0)) AS TotalScore
            FROM (SELECT DISTINCT tc.UserID, tc.TaskID
                  FROM [grabit].[TaskCollaborators] tc
                  JOIN [grabit].[Tasks] t ON t.TaskID = tc.TaskID
                  WHERE tc.isActive = 1) AS distinct_user_tasks
            JOIN [grabit].[Users] u ON u.UserID = distinct_user_tasks.UserID
            JOIN [grabit].[Tasks] t ON t.TaskID = distinct_user_tasks.TaskID
            JOIN [grabit].[TaskPoints] tp ON tp.TaskPointID = t.TaskPointID
            WHERE t.TaskStatusID = 4 AND t.ProjectID = :projectID
            GROUP BY u.UserID, u.GitHubID
            ORDER BY TotalScore DESC
            """, nativeQuery = true)
    List<Object[]> getProjectLeaderboard(@Param("projectID") Integer projectID);

}
