package com.grabit.app.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grabit.app.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {
    private Integer taskID;
    private String taskName;
    private String taskDescription;
    private Timestamp taskCreatedAt;
    private Byte taskPointID;
    private Byte taskStatusID;
    private LocalDateTime taskReviewRequestedAt;

    public TaskDTO(Task task) {
        this.taskID = task.getTaskID();
        this.taskName = task.getTaskName();
        this.taskDescription = task.getTaskDescription();
        this.taskCreatedAt = Timestamp.valueOf(task.getTaskCreatedAt());
        this.taskPointID = task.getTaskPoint().getTaskPointID();
        this.taskStatusID = task.getTaskStatus().getTaskStatusID();
        this.taskReviewRequestedAt = task.getTaskReviewRequestedAt();
    }

    public static List<TaskDTO> getTasks(List<Task> tasks) {

        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            taskDTOs.add(new TaskDTO(task));
        }
        return taskDTOs;
    }

}