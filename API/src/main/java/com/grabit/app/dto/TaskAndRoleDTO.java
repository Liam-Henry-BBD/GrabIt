package com.grabit.app.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.grabit.app.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAndRoleDTO {
    private Integer taskID;
    private String taskName;
    private String taskDescription;
    private Byte taskPointID;
    private Byte taskStatusID;
    private Integer userID;
    private boolean isActive;
    private Integer projectID;
    private Timestamp taskCreatedAt;
    private Timestamp taskReviewRequestedAt;
    private Timestamp taskCompletedAt;
    private Timestamp taskDeadline;
    private Timestamp taskUpdatedAt;

}