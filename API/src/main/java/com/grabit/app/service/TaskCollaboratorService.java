package com.grabit.app.service;

import com.grabit.app.enums.Roles;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.grabit.app.model.Role;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Service
public class TaskCollaboratorService {

    private final TaskCollaboratorRepository taskCollaboratorRepository;

    private final TaskRepository taskRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;
    private final RoleRepository roleRepository;

    public TaskCollaboratorService(TaskCollaboratorRepository taskCollaboratorRepository,
            TaskRepository taskRepository,
            RoleRepository roleRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.taskCollaboratorRepository = taskCollaboratorRepository;
        this.taskRepository = taskRepository;
        this.roleRepository = roleRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
    }

    @Transactional
    public void addTaskCollaborator(TaskCollaborator taskCollaborator, User user) {

        Role role = roleRepository.findById(Integer.valueOf(taskCollaborator.getRole().getRoleID()))
                .orElseThrow(() -> new NotFound("Role does not exist."));
        Task task = taskRepository.findById(taskCollaborator.getTask().getTaskID())
                .orElseThrow(() -> new NotFound("Task not found."));

        //Before you get added as a task collaborator, you must be a project collaborator first.
        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectID(user.getUserID(),task.getProject().getProjectID());
        if (!allowed) {
            throw new BadRequest("Cannot add collaborator: User is not a member of this project.");
        }

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Cannot add collaborator: Task is already complete.");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Cannot add collaborator: Task deadline has passed.");
        }

        taskCollaboratorRepository.createCollaborator(LocalDate.now(), user.getUserID(), role.getRoleID(), task.getTaskID());
    }

    public List<TaskCollaborator> getAllTaskCollaborators() {
        //Can all
        return taskCollaboratorRepository.findAll();
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer taskCollabID) {
        return taskCollaboratorRepository.findById(taskCollabID)
                .orElseThrow(() -> new NotFound("TaskCollaborator not found with ID: " + taskCollabID));
    }

    public TaskCollaborator deactivateTaskCollaboratorByID(Integer taskCollabID, User user) {

        boolean allowed = taskCollaboratorRepository.existsByIdAndUserIDAndRoleID(taskCollabID, user.getUserID(), Roles.TASK_GRABBER.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot deactivate this collaborator.");
        }

        TaskCollaborator taskCollaborator = taskCollaboratorRepository.findById(taskCollabID).orElseThrow(() -> new NotFound("TaskCollaborator not found"));

        if (!taskCollaborator.getIsActive()) {
            throw new BadRequest("Task Collaborator is already not active");
        }
        taskCollaborator.setIsActive(false);
        taskCollaboratorRepository.save(taskCollaborator);
        return taskCollaborator;
    }

    @Transactional
    public TaskCollaborator activateTaskCollaboratorByID(Integer taskCollabID, User user) {

        boolean allowed = taskCollaboratorRepository.existsByIdAndUserIDAndRoleID(taskCollabID, user.getUserID(), Roles.TASK_GRABBER.getRole());

        //If you are not grabber, you cannot reinstate collaborators
        if (!allowed) {
            throw new BadRequest("Cannot activate this collaborator.");
        }

        TaskCollaborator taskCollaborator = taskCollaboratorRepository.findById(taskCollabID).orElseThrow(() -> new NotFound("TaskCollaborator not found"));

        if (!taskCollaborator.getIsActive()) {
            throw new BadRequest("Task Collaborator is already active");
        }

        taskCollaboratorRepository.updateActiveStatus(taskCollaborator.getTaskCollaboratorID());
        return taskCollaborator;
    }

    public List<TaskCollaborator> getCollaboratorByTaskID(Integer taskID) {
        return taskCollaboratorRepository.findByTaskID(taskID);
    }
}
