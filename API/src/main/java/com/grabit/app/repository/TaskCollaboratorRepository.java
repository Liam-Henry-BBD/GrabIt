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
    void createCollaborator(LocalDate joinedAt, Integer userID, Byte roleID, Integer taskID);


    @Query(value = "SELECT CASE WHEN COUNT(tc) > 0 THEN TRUE ELSE FALSE END FROM TaskCollaborator tc WHERE tc.task.taskID = :taskID AND tc.user.userID = :userID")
    boolean existsByTaskIDAndUserID(Integer taskID, Integer userID);

    @Modifying
    @Query(value = "UPDATE TaskCollaborator tc SET tc.isActive = false  WHERE tc.task.taskID = :taskID")
    void deactivateCollaborators(Integer taskID);

    @Query(value = "SELECT CASE WHEN COUNT(tc) > 0 THEN TRUE ELSE FALSE END FROM TaskCollaborator tc WHERE tc.task.taskID = :taskID AND tc.user.userID = :userID AND tc.role.roleID = :roleID")
    boolean existsByIdAndUserIDAndRoleID(Integer taskID, Integer userID, Byte roleID);

    @Query(value = "SELECT CASE WHEN COUNT(tc) > 0 THEN TRUE ELSE FALSE END FROM TaskCollaborator tc WHERE tc.user.userID = :userID AND tc.task.taskID = :taskID")
    boolean existsByUserIDAndTaskID(Integer userID, Integer taskID);

    @Query(value = "SELECT * FROM TaskCollaborators tc WHERE TaskID = :taskID AND UserID = :userID", nativeQuery = true)
    TaskCollaborator findByTaskIDAndUserID(@Param("taskID") Integer taskID, @Param("userID") Integer userID);

    @Query(value = "SELECT CASE WHEN COUNT(tc) > 0 THEN TRUE ELSE FALSE END FROM TaskCollaborator tc WHERE tc.task.taskID = :taskID AND tc.role.roleID = :roleID")
    boolean existsByTaskIDAndRoleID(Integer taskID, byte roleID);

    @Query(value = "SELECT * FROM TaskCollaborators tc WHERE TaskID = :taskID AND RoleID = :roleID", nativeQuery = true)
    List<TaskCollaborator> findByTaskIDAndRoleID(@Param("taskID") Integer taskID, @Param("roleID") Byte roleID);

    @Query(value = "SELECT * FROM TaskCollaborators tc WHERE TaskID = :taskID AND RoleID = :roleID AND IsActive = 1", nativeQuery = true)
    List<TaskCollaborator> findByTaskIDAndRoleIDAndIsActive(@Param("taskID") Integer taskID, @Param("roleID") Byte roleID);

    @Query(value = "SELECT * FROM TaskCollaborators tc WHERE TaskID = :taskID AND IsActive = 1", nativeQuery = true)
    List<TaskCollaborator> findByTaskIDAndIsActive(@Param("taskID") Integer taskID);

    @Query(value = "SELECT * FROM TaskCollaborators tc WHERE TaskID = :taskID AND UserID = :userID AND RoleID = :roleID", nativeQuery = true)
    boolean existsByTaskIDAndUserIDAndRoleID(Integer taskID, Integer userID, byte roleID);
}