package com.grabit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM Tasks task WHERE ProjectID = :projectID", nativeQuery = true)
    List<Task> findByProjectID(Integer projectID);

    @Query(value = "SELECT task FROM Task task WHERE task.taskStatus.taskStatusID = :taskStatusID")
    List<Task> findByTaskStatusID(Integer taskStatusID);

    @Query(value = "SELECT CASE WHEN COUNT(task) > 0 THEN TRUE ELSE FALSE END FROM Task task JOIN ProjectCollaborator pc ON pc.projectID = task.project.projectID WHERE pc.userID = :userID AND task.taskID = :taskID")
    boolean existsTaskByUserIDAndTaskID(Integer taskID, Integer userID);
}
