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

    @Query(value = "SELECT * FROM Tasks task WHERE TaskStatusID = :taskStatusID", nativeQuery = true)
    List<Task> findByTaskStatusID(Integer taskStatusID);
}
