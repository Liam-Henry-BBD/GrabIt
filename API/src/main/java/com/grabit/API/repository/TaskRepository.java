package com.grabit.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.API.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // the rest of the methods that are inherited from JpaRepository go here
    
}
