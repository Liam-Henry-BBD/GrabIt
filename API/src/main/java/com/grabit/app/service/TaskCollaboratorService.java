package com.grabit.app.service;

import com.grabit.app.enums.Roles;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grabit.app.model.Role;
import com.grabit.app.model.Task;
import com.grabit.app.model.TaskCollaborator;
import com.grabit.app.model.User;

import java.time.LocalDate;

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

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_MEMBER.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot add collaborator.");
        }

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Cannot add collaborator: Task is already complete.");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Cannot add collaborator: Task deadline has passed.");
        }

        if (taskCollaboratorRepository.existsByUserIDAndTaskID(user.getUserID(), task.getTaskID())) {
            throw new BadRequest("User is already a collaborator for this task.");
        }

        if (role.getRoleID() == Roles.TASK_GRABBER.getRole()) {
            if (taskCollaboratorRepository.existsByTaskIDAndRoleID(task.getTaskID(), Roles.TASK_GRABBER.getRole())) {
                throw new BadRequest("Task already has a grabber.");
            }
        }

        taskCollaboratorRepository.createCollaborator(LocalDate.now(), user.getUserID(), role.getRoleID(),
                task.getTaskID());
    }

    public TaskCollaborator getTaskCollaboratorByID(Integer taskCollabID, User user) {

        Optional<TaskCollaborator> taskCollaborator = taskCollaboratorRepository.findById(taskCollabID);

        if (taskCollaborator.isEmpty()) {
            throw new NotFound("TaskCollaborator not found.");
        }

        if (!taskCollaborator.get().getUser().getUserID().equals(user.getUserID())) {
            throw new BadRequest("User does not belong to this task.");
        }

        if (!taskCollaborator.get().getIsActive()) {
            throw new BadRequest("Task Collaborator is not active.");
        }

        if (taskCollaborator.get().getTask().getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Task is already complete.");
        }

        if (taskCollaborator.get().getTask().getTaskDeadline() != null
                && taskCollaborator.get().getTask().getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Task deadline has passed.");
        }

        return taskCollaborator.get();
    }

    public TaskCollaborator deactivateTaskCollaboratorByID(Integer taskCollabID, User user) {

        boolean allowed = taskCollaboratorRepository.existsByIdAndUserIDAndRoleID(taskCollabID, user.getUserID(),
                Roles.TASK_GRABBER.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot deactivate this collaborator.");
        }

        TaskCollaborator taskCollaborator = taskCollaboratorRepository.findById(taskCollabID)
                .orElseThrow(() -> new NotFound("TaskCollaborator not found"));

        if (!taskCollaborator.getIsActive()) {
            throw new BadRequest("Task Collaborator is already not active");
        }

        if (taskCollaborator.getTask().getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Cannot deactivate collaborator: Task is already complete.");
        }

        if (taskCollaborator.getTask().getTaskDeadline() != null
                && taskCollaborator.getTask().getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Cannot deactivate collaborator: Task deadline has passed.");
        }

        if (taskCollaborator.getRole().getRoleID() == Roles.TASK_GRABBER.getRole()) {
            throw new BadRequest("Cannot deactivate grabber.");
        }

        if (taskCollaboratorRepository.existsByTaskIDAndRoleID(taskCollaborator.getTask().getTaskID(),
                Roles.TASK_GRABBER.getRole())) {
            throw new BadRequest("Cannot deactivate collaborator: Task already has a grabber.");
        }

        taskCollaborator.setIsActive(false);
        taskCollaboratorRepository.save(taskCollaborator);
        return taskCollaborator;
    }

    @Transactional
    public TaskCollaborator activateTaskCollaboratorByID(Integer taskCollabID, User user) {

        boolean allowed = taskCollaboratorRepository.existsByIdAndUserIDAndRoleID(taskCollabID, user.getUserID(),
                Roles.TASK_GRABBER.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot activate this collaborator.");
        }

        TaskCollaborator taskCollaborator = taskCollaboratorRepository.findById(taskCollabID)
                .orElseThrow(() -> new NotFound("TaskCollaborator not found"));

        if (!taskCollaborator.getIsActive()) {
            throw new BadRequest("Task Collaborator is already active");
        }

        taskCollaboratorRepository.updateActiveStatus(taskCollaborator.getTaskCollaboratorID());
        return taskCollaborator;
    }

}
