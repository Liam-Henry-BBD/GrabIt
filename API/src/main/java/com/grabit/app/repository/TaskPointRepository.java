package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.app.model.TaskPoint;

public interface TaskPointRepository extends JpaRepository<TaskPoint, Integer> {
}
