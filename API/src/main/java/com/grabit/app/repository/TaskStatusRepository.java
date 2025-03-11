package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.app.model.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    TaskStatus findByTaskStatusID(Byte taskStatusID);
}
