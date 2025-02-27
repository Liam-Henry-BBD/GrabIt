package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.TaskCollaborator;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaborator, Integer> {

    @Query(value = "SELECT * FROM TaskCollaborators task WHERE TaskID = :taskID", nativeQuery = true)
    List<TaskCollaborator> findByTaskID(@Param("taskID") Integer taskID);

    @Modifying
    @Query(value = "UPDATE TaskCollaborators SET isActive = 1 WHERE TaskCollaboratorID = :taskCollabID", nativeQuery = true)
    void updateActiveStatus(Integer taskCollabID);


    @Modifying
    @Query(value = "INSERT INTO TaskCollaborators(JoinedAt, UserID, RoleID, TaskID) VALUES(:joinedAt, :userID, :roleID, :taskID)", nativeQuery = true)
    void insertCollaborator(LocalDate joinedAt, Integer userID, Byte roleID, Integer taskID);

}