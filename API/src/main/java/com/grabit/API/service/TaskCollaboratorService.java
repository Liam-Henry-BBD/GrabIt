package com.grabit.API.service;

import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.repository.TaskCollaboratorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCollaboratorService {

    @Autowired
    private TaskCollaboratorRepository taskCollaboratorRepository;

    public TaskCollaborator addTaskCollaborator(TaskCollaborator taskCollaborator) {
        return taskCollaboratorRepository.save(taskCollaborator);
    }


    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorRepository.findAll();
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer id) {
        return taskCollaboratorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("TaskCollaborator not found with ID: " + id)
        );
    }


    public ResponseEntity<Object> deleteTaskCollaboratorByID(Integer id) {
        return taskCollaboratorRepository.findById(id).map(taskCollaborator -> {
            taskCollaborator.setIsActive(false);
            taskCollaboratorRepository.save(taskCollaborator);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("TaskCollaborator not found"));
    }

    public List<TaskCollaborator> getCollaboratorsByTaskID(Integer taskID) {
        return taskCollaboratorRepository.findByTaskID(taskID);
    }
}
