package com.grabit.app.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
public class TaskDTO {
    private Integer taskID;
    private String taskName;
    private String taskDescription;
    private LocalDateTime taskCreatedAt;
    private Byte taskPointID;
    private Byte taskStatusID;
    private LocalDateTime taskReviewRequestedAt;

}