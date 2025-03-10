package com.grabit.app.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer taskID;
    private String taskName;
    private String taskDescription;
    private Byte taskPointID;
    private Byte taskStatusID;
    private LocalDateTime taskReviewRequestedAt;
    private Timestamp taskCreatedAt;
}