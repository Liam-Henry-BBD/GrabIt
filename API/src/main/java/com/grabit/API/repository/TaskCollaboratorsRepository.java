package com.grabit.API.repository;

import com.grabit.API.model.TaskCollaborators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCollaboratorsRepository extends JpaRepository<TaskCollaboratorsRepository, Integer> {

}