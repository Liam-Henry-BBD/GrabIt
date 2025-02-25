package com.grabit.API.service;

import com.grabit.API.model.Role;
import com.grabit.API.model.Task;
import com.grabit.API.model.TaskCollaborator;
import com.grabit.API.model.User;
import com.grabit.API.repository.RoleRepository;
import com.grabit.API.repository.TaskCollaboratorRepository;
import com.grabit.API.repository.TaskRepository;
import com.grabit.API.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import java.util.List;

@Service
public class TaskCollaboratorService {

    private final TaskCollaboratorRepository taskCollaboratorRepository;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public TaskCollaboratorService(TaskCollaboratorRepository taskCollaboratorRepository,
            TaskRepository taskRepository,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.taskCollaboratorRepository = taskCollaboratorRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public TaskCollaborator addTaskCollaborator(TaskCollaborator taskCollaborator) {

        User user = userRepository.findById(taskCollaborator.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(Integer.valueOf(taskCollaborator.getRole().getRoleId()))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Task task = taskRepository.findById(taskCollaborator.getTask().getTaskID())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot add collaborator: Task is already complete");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot add collaborator: Task deadline has passed");
        }

        try {
            TaskCollaborator newTaskCollaborator = new TaskCollaborator();
            newTaskCollaborator.setTask(task);
            newTaskCollaborator.setUser(user);
            newTaskCollaborator.setRole(role);
            newTaskCollaborator.setJoinedAt(LocalDate.now());
            return taskCollaboratorRepository.save(newTaskCollaborator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaskCollaborator> getAllTaskCollaborators() {
        return taskCollaboratorRepository.findAll();
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "TaskCollaborator not found with ID: " + taskCollabID));
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
        return taskCollaboratorRepository.findByTask_TaskID(taskID);
    }
}
