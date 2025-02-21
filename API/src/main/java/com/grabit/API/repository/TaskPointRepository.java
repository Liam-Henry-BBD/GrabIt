package com.grabit.API.service;

import com.grabit.API.model.TaskPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPointRepository extends JpaRepository<TaskPoint, Integer> {
}
