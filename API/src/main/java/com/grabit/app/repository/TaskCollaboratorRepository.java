package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.TaskCollaborator;

import java.util.List;

@Repository
public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaborator, Integer> {

    @Query(value = "SELECT * FROM TaskCollaborators task WHERE TaskID = :taskID", nativeQuery = true)
    List<TaskCollaborator> findByTaskID(@Param("taskID") Integer taskID);
}