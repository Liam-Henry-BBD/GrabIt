package com.grabit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.api.model.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
}
