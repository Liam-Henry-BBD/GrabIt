package com.grabit.API.service;

import com.grabit.API.model.Task;
import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.repository.TaskCollaboratorRepository;
import com.grabit.API.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
// import java.util.Optional;

@Service
public class TaskCollaboratorService {

    @Autowired
    private TaskCollaboratorRepository taskCollaboratorRepository;

    @Autowired
    private TaskRepository taskRepository;

    public TaskCollaborator addTaskCollaborator(TaskCollaborator taskCollaborator) {
        // Check if the task exists
        Task task = taskRepository.findById(taskCollaborator.getTask().getTaskID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        // Need to check if user exists but I will need a user model and user repository for those... will revisit.
        // Check if the task is complete
        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add collaborator: Task is already complete");
        }

        // Check if the deadline has passed
        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add collaborator: Task deadline has passed");
        }

        // Check if user is already a collaborator for the task
//        Optional<TaskCollaborator> existingCollaborator = taskCollaboratorRepository.findByUserIDAndTaskID(
//                taskCollaborator.getUserID(), taskCollaborator.getTaskID());

//        if (existingCollaborator.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already a collaborator for this task");
//        }

        return taskCollaboratorRepository.save(taskCollaborator);
    }

    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorRepository.findAll();
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskCollaborator not found with ID: " + taskCollabID)
        );
    }

    public ResponseEntity<Object> deactivateTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID).map(taskCollaborator -> {
            if (!taskCollaborator.getIsActive()) {
                throw new RuntimeException("Task Collaborator is already not active");
            }
            taskCollaborator.setIsActive(false);
            taskCollaboratorRepository.save(taskCollaborator);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("TaskCollaborator not found"));
    }

    public ResponseEntity<Object> activateTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID).map(taskCollaborator -> {
            if (taskCollaborator.getIsActive()) {
                throw new RuntimeException("Task Collaborator is already active");
            }
            taskCollaborator.setIsActive(true);
            taskCollaboratorRepository.save(taskCollaborator);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("TaskCollaborator not found"));
    }

    public List<TaskCollaborator> getCollaboratorByTaskID(Integer taskID) {
        return taskCollaboratorRepository.findByTaskID(taskID);
    }
}
