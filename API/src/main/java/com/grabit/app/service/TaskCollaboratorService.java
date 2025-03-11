package com.grabit.app.service;

import com.grabit.app.enums.Roles;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.model.*;
import com.grabit.app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


        ProjectCollaborator privileges = projectCollaboratorRepository
                .findByProjectIDAndUserID(task.getProject().getProjectID(), user.getUserID());

        if (privileges == null) {
            throw new BadRequest("User is not a collaborator on this project");
        }

        TaskCollaborator adderIsCollaborator = taskCollaboratorRepository
                .findByTaskIDAndUserID(task.getTaskID(), user.getUserID());

        TaskCollaborator collaborator = taskCollaboratorRepository
                .findByTaskIDAndUserID(task.getTaskID(), taskCollaborator.getUser().getUserID());

        if (!(privileges.getRoleID().equals(Roles.PROJECT_LEAD.getRole()))
                && !(adderIsCollaborator.getRole().getRoleID() == Roles.TASK_GRABBER.getRole())) {
            throw new BadRequest("Only project leads and grabbers can add task collaborators.");
        }

        boolean collaboratorExists = projectCollaboratorRepository
                .existsByUserIDAndProjectID(taskCollaborator.getUser().getUserID(), task.getProject().getProjectID());

        if (!collaboratorExists) {
            throw new BadRequest("User cannot be added as a task collaborator in a project they are not a collaborator in.");
        }

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Cannot add collaborator: Task is already complete.");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Cannot add collaborator: Task deadline has passed.");
        }

        if (collaborator != null) {
            throw new BadRequest("User is already a collaborator for this task.");
        }

        if (role.getRoleID() == Roles.TASK_GRABBER.getRole()) {
            if (taskCollaboratorRepository.existsByTaskIDAndRoleID(task.getTaskID(), Roles.TASK_GRABBER.getRole())) {
                throw new BadRequest("Task already has a grabber.");
            }
        }

        if (!taskCollaborator.getRole().getRoleID().equals(Roles.TASK_GRABBER.getRole())
                && !taskCollaborator.getRole().getRoleID().equals(Roles.TASK_COLLABORATOR.getRole())) {
            throw new BadRequest("User can only be a grabber(roleId:3) or collaborator(roleId:4) on a task");
        }

        taskCollaboratorRepository.createCollaborator(LocalDate.now(),
                taskCollaborator.getUser().getUserID(),
                taskCollaborator.getRole().getRoleID(),
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

        TaskCollaborator collaborator = taskCollaboratorRepository.findById(taskCollabID).get();

        Task task = taskRepository.findById(collaborator.getTask().getTaskID()).get();

        boolean isLead = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(), task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());


        if (!allowed && !isLead) {
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
