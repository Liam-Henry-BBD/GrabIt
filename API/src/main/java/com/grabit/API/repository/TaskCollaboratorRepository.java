package com.grabit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.api.model.TaskCollaborator;

import java.util.List;

@Repository
public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaborator, Integer> {
    List<TaskCollaborator> findByTask_TaskID(Integer taskID);
}