package com.grabit.API.repository;

 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import com.grabit.API.model.TaskCollaborators;

@Repository
public interface TaskCollaboratorsRepository extends JpaRepository<TaskCollaborators, Integer> {

}