package com.grabit.API.service;

import java.time.LocalDate;
import java.util.List;

//import com.grabit.API.model.TaskCollaborators;
import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.model.TaskStatus;
import com.grabit.API.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.grabit.API.model.Task;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    //Other repos
    private final TaskStatusRepository taskStatusRepository;
    private final TaskPointRepository taskPointRepository;
    private final ProjectRepository projectRepository;
    private final TaskCollaboratorRepository taskCollaboratorRepository;


    public TaskService(TaskRepository taskRepository,
                       TaskStatusRepository taskStatusRepository,
                       TaskPointRepository taskPointRepository,
                       ProjectRepository projectRepository,
                       TaskCollaboratorRepository taskCollabRepository
    ) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.taskPointRepository = taskPointRepository;
        this.projectRepository = projectRepository;
        this.taskCollaboratorRepository = taskCollabRepository;
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

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task already completed");
        }

        if (taskStatusID == 4 && !task.getTaskStatus().getStatusName().contains("Review")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must be reviewed before it can be completed");
        }

        if (task.getTaskStatus().getStatusName().contains("Review") && taskStatusID == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot move task from review to available.");
        }

        if (task.getTaskStatus().getStatusName().contains("Available") && taskStatusID != 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must move from available to grabbed.");
        }

        //TODO: If a task has been taken for a day, it cannot be move to available again
        TaskStatus taskStatus = taskStatusRepository.findById((int) taskStatusID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task status not found"));


        task.setTaskStatus(taskStatus);

        return taskRepository.save(task);
    }

    public Task updateTask(Integer taskID, Task task) {
        Task updatedTask = taskRepository.findById(taskID)
            .orElseThrow(() -> new RuntimeException("Task not found."));

        if (updatedTask.getTaskStatus().getStatusName().contains("Complete")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task is already completed.");
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
            .orElseThrow(() -> new RuntimeException("Task not found."));
        
        taskRepository.delete(task);
    }

    public List<TaskCollaborator> getTaskCollaborators(Integer taskID) {

        Boolean taskExists = taskRepository.existsById(taskID);
        if (!taskExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        return taskCollaboratorRepository.findByTaskID(taskID);
    }

}
