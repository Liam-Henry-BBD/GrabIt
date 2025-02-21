package com.grabit.API.service;

import com.grabit.API.model.TaskCollaborators;
import com.grabit.API.repository.TaskCollaboratorsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCollaboratorsService {

    @Autowired
    private TaskCollaboratorsRepository taskCollaboratorsRepository;

    public List<TaskCollaborators> getAllTaskCollaborators() {
        return taskCollaboratorsRepository.findAll();
    }

    public TaskCollaborators addTaskCollaborators(TaskCollaborators taskCollaborators) {
        return taskCollaboratorsRepository.save(taskCollaborators);
    }

    public TaskCollaborators getTaskCollaboratorsByID(Integer id) {
        return taskCollaboratorsRepository.findById(id.intValue()).orElse(null);
    }

    public TaskCollaborators updateTaskCollaborators(Integer id, TaskCollaborators taskCollaborators) {
        taskCollaborators.setTaskCollaboratorID(id.intValue());
        TaskCollaborators TaskCollaborators = new TaskCollaborators();

        return taskCollaboratorsRepository.save(TaskCollaborators);
    }

    public void deleteTaskCollaboratorsByID(Integer id) {
        taskCollaboratorsRepository.deleteById(id.intValue());
    }
}
