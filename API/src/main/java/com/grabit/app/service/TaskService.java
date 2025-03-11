package com.grabit.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.grabit.app.dto.TaskDTO;
import com.grabit.app.enums.Roles;
import com.grabit.app.enums.Status;
import com.grabit.app.exceptions.BadRequest;
import com.grabit.app.exceptions.NotFound;
import com.grabit.app.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grabit.app.repository.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskPointRepository taskPointRepository;
    private final TaskCollaboratorRepository taskCollaboratorRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    public TaskService(TaskRepository taskRepository,
            TaskStatusRepository taskStatusRepository,
            TaskPointRepository taskPointRepository,
            TaskCollaboratorRepository taskCollabRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.taskPointRepository = taskPointRepository;
        this.taskCollaboratorRepository = taskCollabRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;
    }

    public TaskDTO getTaskById(Integer taskID, User user) {

        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new NotFound("Task not found"));

        TaskDTO taskDTO = new TaskDTO(task.getTaskID(), task.getTaskName(), task.getTaskDescription(),
                task.getTaskCreatedAt(), task.getTaskPoint().getTaskPointID(), task.getTaskStatus().getTaskStatusID(),
                task.getTaskReviewRequestedAt());

        boolean allowed = taskRepository.existsTaskByUserIDAndTaskID(taskID, user.getUserID());

        boolean isLead = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());

        if (!allowed && !isLead) {
            throw new BadRequest("Cannot access task because you are not a project collaborator.");
        }

        return taskDTO;
    }

    public void createTask(Task task, User user) {

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());

        if (!allowed) {
            throw new BadRequest("User is not a lead of this project.");
        }

        if (!taskPointRepository.existsByTaskPointID(task.getTaskPoint().getTaskPointID())) {
            throw new BadRequest("Task point not found.");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Task deadline cannot be in the past.");
        }
        task.setTaskStatus(new TaskStatus((byte) 1, "Available"));
        taskRepository.save(task);
    }

    // is Allowed needs to be changed
    public Task updateTaskStatus(int taskID, byte taskStatusID, User user) {

        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new NotFound("Task not found"));

        boolean isLead = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());
        if (taskStatusID == Status.COMPLETE.getStatus() && !isLead) {
            throw new BadRequest("User is not a lead in this project.");
        }

        boolean allowed = taskCollaboratorRepository.existsByTaskIDAndUserID(taskID, user.getUserID());
        if (!allowed && !isLead) {
            throw new BadRequest("User is not a member of this task.");
        }

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Task is already completed.");
        }

        if (taskStatusID == Status.AVAILABLE.getStatus()
                && task.getTaskStatus().getTaskStatusID() != Status.AVAILABLE.getStatus()) {
            throw new BadRequest("Task status cannot move to available.");
        }

        if (taskStatusID == Status.COMPLETE.getStatus()
                && !task.getTaskStatus().getStatusName().contains("Review")) {
            throw new BadRequest("Task must be reviewed before it can be completed.");
        }

        if (task.getTaskStatus().getTaskStatusID() == Status.AVAILABLE.getStatus()
                && taskStatusID != Status.GRABBED.getStatus()) {
            throw new BadRequest("Task must move from available to grabbed.");
        }

        if (task.getTaskStatus().getTaskStatusID() == Status.GRABBED.getStatus()
                && taskStatusID != Status.REVIEW.getStatus()) {
            throw new BadRequest("Task must move from grabbed to review.");
        }

        if (task.getTaskStatus().getTaskStatusID() == Status.REVIEW.getStatus()) {
            task.setTaskReviewRequestedAt(LocalDateTime.now());
        }

        TaskStatus taskStatus = taskStatusRepository.findById((int) taskStatusID)
                .orElseThrow(() -> new NotFound("Task status not found."));

        task.setTaskStatus(taskStatus);

        return taskRepository.save(task);
    }

    public Task updateTask(Integer taskID, Task task, User user) {

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());
        if (!allowed) {
            throw new BadRequest("Cannot update task. Not a project lead.");
        }

        Task updatedTask = taskRepository.findById(taskID)
                .orElseThrow(() -> new BadRequest("Task not found."));

        if (updatedTask.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Task is already completed.");
        }

        if (task.getTaskDeadline() != null && task.getTaskDeadline().isBefore(LocalDate.now())) {
            throw new BadRequest("Task deadline cannot be in the past.");
        }
        updatedTask.setTaskName(task.getTaskName());
        updatedTask.setTaskDescription(task.getTaskDescription());
        updatedTask.setTaskDeadline(task.getTaskDeadline());
        updatedTask.setTaskStatus(task.getTaskStatus());
        updatedTask.setActive(task.isActive());
        return taskRepository.save(updatedTask);
    }

    @Transactional
    public void deleteTask(Integer id, User user) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFound("Task not found."));

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(),
                Roles.PROJECT_LEAD.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot delete task. Not a project lead.");
        }

        taskRepository.deactivateTask(id);
    }

    public List<TaskCollaborator> getTaskCollaborators(Integer taskID, User user) {

        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new NotFound("Task not found."));

        ProjectCollaborator collaborator = projectCollaboratorRepository
                .findByProjectIDAndUserID(task.getProject().getProjectID(), user.getUserID());
        if (collaborator == null) {
            throw new NotFound("Collaborator not found.");
        }
        return taskCollaboratorRepository.findByTaskID(taskID);
    }

    @Transactional
    public Task grabTask(Integer taskID, User user) {

        Task task = taskRepository.findById(taskID).orElseThrow(() -> new NotFound("Task not found."));
        if (task.getTaskStatus().getTaskStatusID() != Status.AVAILABLE.getStatus()) {
            throw new BadRequest("Task is not available to be grabbed.");
        }

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(),
                Roles.PROJECT_MEMBER.getRole());

        if (!allowed) {
            throw new BadRequest("You cannot grab this task. User is not a collaborator.");
        }

        boolean alreadyCollaborator = taskCollaboratorRepository.existsByTaskIDAndUserID(taskID, user.getUserID());
        if (alreadyCollaborator) {
            throw new BadRequest("User is already a collaborator.");
        }

        taskCollaboratorRepository.createCollaborator(LocalDate.now(), user.getUserID(), Roles.TASK_GRABBER.getRole(),
                task.getTaskID());

        TaskStatus newStatus = taskStatusRepository.findById((int) Status.GRABBED.getStatus())
                .orElseThrow(() -> new NotFound("Task status not found."));
        task.setTaskStatus(newStatus);
        return taskRepository.save(task);
    }
}
