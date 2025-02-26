package com.grabit.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.api.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProject_ProjectID(Integer Project_ProjectId);

    List<Task> findByTaskStatus_TaskStatusID(Integer taskStatusID);
}
