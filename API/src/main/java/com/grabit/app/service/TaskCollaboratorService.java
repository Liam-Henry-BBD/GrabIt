package com.grabit.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.grabit.app.model.Role;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;
import com.grabit.app.repository.RoleRepository;
import com.grabit.app.repository.TaskCollaboratorRepository;
import com.grabit.app.repository.TaskRepository;
import com.grabit.app.repository.UserRepository;

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

    @Transactional
    public void addTaskCollaborator(TaskCollaborator taskCollaborator) {

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

        taskCollaborator.setJoinedAt(LocalDate.now());
        taskCollaboratorRepository.insertCollaborator(taskCollaborator.getJoinedAt(),
                taskCollaborator.getUser().getUserId(),
                taskCollaborator.getRole().getRoleId(),
                taskCollaborator.getTask().getTaskID()
        );
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

    @Transactional
    public ResponseEntity<Object> activateTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID).map(taskCollaborator -> {
            if (taskCollaborator.getIsActive()) {
                throw new RuntimeException("Task Collaborator is already active");
            }
//            taskCollaborator.setIsActive(true);
            taskCollaboratorRepository.updateActiveStatus(taskCollaborator.getTaskCollaboratorId());
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("TaskCollaborator not found"));
    }

    public List<TaskCollaborator> getCollaboratorByTaskID(Integer taskID) {
        return taskCollaboratorRepository.findByTaskID(taskID);
    }
}
