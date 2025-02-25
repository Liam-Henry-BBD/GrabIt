package com.grabit.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.API.model.TaskCollaborator;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaborator, Integer> {
    List<TaskCollaborator> findByTask_TaskID(Integer taskID);

//    Optional<TaskCollaborator> findByUserIDAndTaskID(Integer userID, Integer taskID);

}