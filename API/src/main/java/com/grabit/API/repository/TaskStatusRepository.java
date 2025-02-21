package com.grabit.API.repository;

import com.grabit.API.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
}
