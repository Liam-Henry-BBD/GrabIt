package com.grabit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.dto.TaskAndRoleDTO;
import com.grabit.app.dto.TaskDTO;
import com.grabit.app.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM Tasks task WHERE ProjectID = :projectID", nativeQuery = true)
    List<Task> findByProjectID(Integer projectID);

    @Query(value = "SELECT task.TaskID, task.TaskName,task.TaskDescription,task.TaskCreatedAt,task.TaskStatusID,task.TaskPointID,task.TaskReviewRequestedAt FROM Tasks task join ProjectCollaborators pc on task.ProjectID=pc.ProjectID WHERE task.ProjectID = :projectID AND pc.UserID = :userID", nativeQuery = true)

    List<TaskDTO> findByProjectIDAndUserID(Integer projectID, Integer userID);

    @Query(value = "SELECT task FROM Task task WHERE task.taskStatus.taskStatusID = :taskStatusID")
    List<Task> findByTaskStatusID(Integer taskStatusID);

    @Query(value = "SELECT CASE WHEN COUNT(task) > 0 THEN TRUE ELSE FALSE END FROM Task task JOIN ProjectCollaborator pc ON pc.projectID = task.project.projectID WHERE pc.userID = :userID AND task.taskID = :taskID")
    boolean existsTaskByUserIDAndTaskID(Integer taskID, Integer userID);

    @Modifying
    @Query(value = "UPDATE Task SET isActive = false WHERE taskID = :taskID")
    void deactivateTask(@Param("taskID") Integer taskID);

    @Query(value = "SELECT * FROM Tasks task WHERE TaskID = :taskID", nativeQuery = true)
    Task findByTaskID(Integer taskID);

    @Query(value = "SELECT * FROM Tasks task WHERE TaskPointID = :taskPointID", nativeQuery = true)
    List<Task> findByTaskPointID(Integer taskPointID);


    @Query(value = """
            SELECT 
                task.TaskID,
                task.TaskName,
                task.TaskDescription,
                task.TaskPointID,
                task.TaskStatusID,
                collab.UserID as userID,
                task.IsActive,
                task.ProjectID,
                task.TaskCreatedAt,
                task.TaskReviewRequestedAt,
                task.TaskCompletedAt,
                task.TaskDeadline,
                task.TaskUpdatedAt
            FROM Tasks task
            LEFT JOIN TaskCollaborators collab
            ON task.TaskID = collab.TaskID
            WHERE 
                task.ProjectID = :projectID
            """, nativeQuery = true)
    List<TaskAndRoleDTO> getTasksWithRoles(Integer projectID);

    @Query(value = """
            SELECT 
                task.TaskID,
                task.TaskName,
                task.TaskDescription,
                task.TaskPointID,
                task.TaskStatusID,
                collab.UserID as userID,
                task.IsActive,
                task.ProjectID,
                task.TaskCreatedAt,
                task.TaskReviewRequestedAt,
                task.TaskCompletedAt,
                task.TaskDeadline,
                task.TaskUpdatedAt
            FROM Tasks task
            JOIN TaskCollaborators collab
            ON task.TaskID = collab.TaskID
            WHERE 
                task.ProjectID = :projectID
                AND collab.UserID = :userID;
            """, nativeQuery = true)
    List<TaskAndRoleDTO> getMyTasksWithRoles(Integer projectID, Integer userID);

}
