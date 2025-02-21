package com.grabit.API.service;

import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.repository.TaskCollaboratorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCollaboratorService {

    @Autowired
    private TaskCollaboratorRepository taskCollaboratorRepository;

    public List<TaskCollaborator> getAllTaskCollaborator() {
        return taskCollaboratorRepository.findAll();
    }

    public TaskCollaborator addTaskCollaborator(TaskCollaborator taskCollaborator) {
        return taskCollaboratorRepository.save(taskCollaborator);
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer id) {
        return taskCollaboratorRepository.findById(id).orElse(null);
    }

    public TaskCollaborator updateTaskCollaborator(Integer id, TaskCollaborator taskCollaborator) {
        taskCollaborator.setTaskCollaboratorID(id);
        TaskCollaborator TaskCollaborator = new TaskCollaborator();

        return taskCollaboratorRepository.save(TaskCollaborator);
    }

    public void deleteTaskCollaboratorByID(Integer id) {
        taskCollaboratorRepository.findById(id).ifPresentOrElse(
                taskCollaborator -> {
                    taskCollaborator.setIsActive(false);
                    taskCollaboratorRepository.save(taskCollaborator);
                },
                () -> {
                    throw new RuntimeException("TaskCollaborator not found");
                }
        );
    }
}
