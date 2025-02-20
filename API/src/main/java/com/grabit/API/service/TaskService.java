package com.grabit.API.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import com.grabit.API.model.Task;
import com.grabit.API.repository.TaskRepository;


@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //TODO: to be removed later
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task task) {
        Task updatedTask = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
      
        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new RuntimeException("Task deadline cannot be in the past.");
        }

        updatedTask.setTaskName(task.getTaskName());
        
        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        taskRepository.delete(task);
    }

}
