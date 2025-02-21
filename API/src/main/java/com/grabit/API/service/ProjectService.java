package com.grabit.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.model.Task;
<<<<<<< Updated upstream
import com.grabit.API.model.TaskCollaborators;
import com.grabit.API.repository.ProjectCollaboratorRepository;
=======
import com.grabit.API.model.TaskCollaborator;
>>>>>>> Stashed changes
import com.grabit.API.repository.ProjectRepository;
import com.grabit.API.repository.TaskCollaboratorRepository;
import com.grabit.API.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService extends Task {

    // private TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
<<<<<<< Updated upstream
    private final TaskCollaboratorsRepository taskCollaboratorsRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
            TaskCollaboratorsRepository taskCollaboratorsRepository,
            ProjectCollaboratorRepository projectCollaboratorRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskCollaboratorsRepository = taskCollaboratorsRepository;
        this.projectCollaboratorRepository = projectCollaboratorRepository;

=======
    private final TaskCollaboratorRepository taskCollaboratorRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
            TaskCollaboratorRepository taskCollaboratorRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.taskCollaboratorRepository = taskCollaboratorRepository;
>>>>>>> Stashed changes
    }

    // Save a new project or update an existing one
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get a project by its ID
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    // Delete a project by ID
    public void closeProject(Integer id) {
        projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(id);
    }

    public List<Task> getProjectTasksByProjectId(Integer projectID) {
        return taskRepository.findByProject_ProjectID(projectID);
    }

    public List<TaskCollaborator> getProjectLeaderboardByProjectId(Integer projectID) {
        List<Task> tasks = taskRepository.findByProject_ProjectID(projectID);
<<<<<<< Updated upstream
        List<Task> completedTasks = tasks.stream().filter(task -> task.getTaskStatus().getTaskStatusID() == 2)
                .collect(Collectors.toList());
        List<TaskCollaborators> taskCollaborators = completedTasks.stream()
                .flatMap(task -> taskCollaboratorsRepository.findById(task.getTaskID()).stream())
                .collect(Collectors.toList());
        // List<Object>
=======
        List<Task> completedTasks = tasks.stream().filter(task -> task.getTaskStatus().getTaskStatusID() == 2).collect(Collectors.toList());
        List<TaskCollaborator> taskCollaborator = completedTasks.stream().flatMap(task -> taskCollaboratorRepository.findById(task.getTaskID()).stream()).collect(Collectors.toList());
        // List<Object> 
>>>>>>> Stashed changes

        return taskCollaborator;
    }

    public List<ProjectCollaboratorModel> getProjectCollaboratorsByProjectId(Integer projectID) {
        return projectCollaboratorRepository.findByProject_ProjectID(projectID);
    }
}
