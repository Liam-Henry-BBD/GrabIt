package com.grabit.API.service;

import java.time.LocalDate;
import java.util.List;

import com.grabit.API.repository.ProjectRepository;
import com.grabit.API.repository.TaskPointRepository;
import com.grabit.API.repository.TaskStatusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.grabit.API.model.Task;
import com.grabit.API.repository.TaskRepository;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    //Other repos
    private final TaskStatusRepository taskStatusRepository;
    private final TaskPointRepository taskPointRepository;
    private final ProjectRepository projectRepository;



    public TaskService(TaskRepository taskRepository, TaskStatusRepository taskStatusRepository, TaskPointRepository taskPointRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.taskPointRepository = taskPointRepository;
        this.projectRepository = projectRepository;
    }


    public List<Task> getTasksByProjectID(Integer projectID) {
        return taskRepository.findByProject_ProjectID(projectID);
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public Task createTask(Task task) {

        if(!projectRepository.existsById(task.getProject().getProjectID())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }

        if (!taskPointRepository.existsById((int) task.getTaskPoint().getTaskPointID())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task point not found");
        }

        if (!taskStatusRepository.existsById((int) task.getTaskStatus().getTaskStatusID())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task status not found");
        }
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(int taskID, byte taskStatusID) {
        Task task = taskRepository.findById(taskID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (task.getTaskStatus().getStatusName().contains("COMPLETED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task already completed");
        }

        //To introduce enums later
        if (taskStatusID == 4 && !task.getTaskStatus().getStatusName().contains("REVIEW")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must be reviewed before it can be completed");
        }

        if (task.getTaskStatus().getStatusName().contains("REVIEW") && taskStatusID == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot move task from review to available.");
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Integer taskID, Task task) {
        Task updatedTask = taskRepository.findById(taskID)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updatedTask.getTaskStatus().getStatusName().contains("COMPLETED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task is already completed");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task deadline cannot be in the past.");
        }

        updatedTask.setTaskName(task.getTaskName());
        updatedTask.setTaskDescription(task.getTaskDescription());
        
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        taskRepository.delete(task);
    }

}
