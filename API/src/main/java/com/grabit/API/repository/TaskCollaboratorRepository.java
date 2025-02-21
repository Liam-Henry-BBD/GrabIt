package com.grabit.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.API.model.TaskCollaborator; 

@Repository
public interface TaskCollaboratorRepository extends JpaRepository<TaskCollaborator, Integer> {

}