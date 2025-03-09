package com.grabit.app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class TaskDTO {
    private String taskName;
    private String taskDescription;
    private LocalDateTime createdAt;
    private String taskStatus;
    private String taskPoint;
    private LocalDateTime taskReviewRequestedAt;
}
