package com.grabit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grabit.api.model.TaskPoint;

public interface TaskPointRepository extends JpaRepository<TaskPoint, Integer> {
}
