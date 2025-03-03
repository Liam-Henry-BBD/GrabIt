package com.grabit.app.service;

import java.time.LocalDate;
import java.util.List;

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

    public Task getTaskById(Integer taskID, User user) {

        boolean allowed = taskRepository.existsTaskByUserIDAndTaskID(taskID, user.getUserID());
        if (!allowed) {
            throw new BadRequest("Cannot access task because you are not a collaborator.");
        }

        return taskRepository.findById(taskID)
                .orElseThrow(() -> new NotFound("Task not found"));
    }

    public Task createTask(Task task, User user) {

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(), Roles.PROJECT_LEAD.getRole());

        if (!allowed) {
            throw new BadRequest("User is not a lead of this project.");
        }

        if (!taskPointRepository.existsById((int) task.getTaskPoint().getTaskPointID())) {
            throw new NotFound("Task point not found.");
        }

        if (!taskStatusRepository.existsById((int) task.getTaskStatus().getTaskStatusID())) {
            throw new NotFound("Task status not found.");
        }
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(int taskID, byte taskStatusID, User user) {

        boolean allowed = taskCollaboratorRepository.existsByTaskIDAndUserID(taskID, user.getUserID());
        if (!allowed) {
            throw new BadRequest("User is not a member of this task.");
        }

        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new NotFound("Task not found"));

        if (task.getTaskStatus().getStatusName().contains("Complete")) {
            throw new BadRequest("Task is already completed.");
        }

        if (taskStatusID == 4 && !task.getTaskStatus().getStatusName().contains("Review")) {
            throw new BadRequest("Task must be reviewed before it can be completed.");
        }

        if (task.getTaskStatus().getStatusName().contains("Review") && taskStatusID == 1) {
            throw new BadRequest("Cannot move task from review to available.");
        }

        if (task.getTaskStatus().getStatusName().contains("Available") && taskStatusID != 2) {
            throw new BadRequest("Task must move from available to grabbed.");
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
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Integer id, User user) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFound("Task not found."));

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                task.getProject().getProjectID(),
                Roles.PROJECT_LEAD.getRole());

        if (!allowed) {
            throw new BadRequest("Cannot delete task. Not a project lead.");
        }

        taskCollaboratorRepository.deactivateCollaborators(id);
    }

    public List<TaskCollaborator> getTaskCollaborators(Integer taskID, User user) {

        boolean allowed = taskRepository.existsTaskByUserIDAndTaskID(taskID, user.getUserID());
        if (!allowed) {
            throw new BadRequest("Cannot access list because you are not a collaborator in this task.");
        }

        return taskCollaboratorRepository.findByTaskID(taskID);
    }

    @Transactional
    public Task grabTask(Integer taskID, int projectID, User user) {

        Task task = taskRepository.findById(taskID).orElseThrow(() -> new NotFound("Task not found."));
        if (task.getTaskStatus().getTaskStatusID() == Status.GRABBED.getStatus()) {
            throw new BadRequest("Task is already grabbed.");
        }

        boolean allowed = projectCollaboratorRepository.existsByUserIDAndProjectIDAndRoleID(user.getUserID(),
                projectID,
                Roles.PROJECT_MEMBER.getRole());

        if (!allowed) {
            throw new BadRequest("User is not a member of this project.");
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
