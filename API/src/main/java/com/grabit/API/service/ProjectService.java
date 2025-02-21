package com.grabit.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grabit.API.model.Project;
// import com.grabit.API.model.Task;
// import com.grabit.API.model.TaskCollaborators;
import com.grabit.API.repository.ProjectRepository;
// import com.grabit.API.repository.TaskRepository;

// import java.util.HashMap;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

@Service
public class ProjectService {

    // private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
    public Optional<Project> getProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    // Delete a project by ID
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }
    // public Optional<List<UserLeaderboardDTO>> getProjectLeaderboard(Integer
    // projectId) {
    // // Fetch the completed tasks for the given project ID
    // List<Task> tasks = taskRepository.findByProjectIdAndStatus(projectId, 4); //
    // status 4 means "completed"

    // if (tasks.isEmpty()) {
    // // Return an empty Optional if there are no completed tasks
    // return Optional.empty();
    // }

    // // Map to store the total points for each user
    // Map<Integer, Integer> userScores = new HashMap<>();

    // // Iterate through the tasks and get the collaborators for each task
    // for (Task task : tasks) {
    // List<TaskCollaborator> taskCollaborators =
    // taskCollaboratorRepository.findByTaskId(task.getId());

    // // For each collaborator of the task, add points to their score
    // for (TaskCollaborator collaborator : taskCollaborators) {
    // User user = collaborator.getUser();
    // userScores.put(user.getId(), userScores.getOrDefault(user.getId(), 0) +
    // task.getPoints());
    // }
    // }

    // // Map the user scores into a list of UserLeaderboardDTO and sort them by
    // score
    // List<UserLeaderboardDTO> leaderboard = userScores.entrySet().stream()
    // .map(entry -> new UserLeaderboardDTO(entry.getKey(), entry.getValue()))
    // .sorted(Comparator.comparingInt(UserLeaderboardDTO::getScore).reversed()) //
    // Sort in descending order of score
    // .collect(Collectors.toList());

    // // Return the leaderboard wrapped in an Optional
    // return Optional.of(leaderboard);
    // }

}
