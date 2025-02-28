package com.grabit.app.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tasks", schema = "grabit")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID", nullable = false)
    private Integer taskID;

    @ManyToOne
    @NotNull(message = "Project ID is required.")
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @NotNull(message = "Task Point ID is required.")
    @JoinColumn(name = "TaskPointID", referencedColumnName = "TaskPointID", nullable = false)
    private TaskPoint taskPoint;

    @ManyToOne
    @NotNull(message = "Task status ID is required.")
    @JoinColumn(name = "TaskStatusID", referencedColumnName = "TaskStatusID", nullable = false)
    private TaskStatus taskStatus;

    @NotBlank(message = "Task name is required.")
    @Column(name = "TaskName", nullable = false, length = 50)
    private String taskName;

    @NotBlank(message = "Task description is required.")
    @Column(name = "TaskDescription", nullable = false, length = 255)
    private String taskDescription;

    @Column(name = "TaskDeadline")
    private LocalDate taskDeadline;

    @Column(name = "TaskCreatedAt", nullable = false)
    private LocalDateTime taskCreatedAt;

    @Column(name = "TaskUpdatedAt")
    private Date taskUpdatedAt;

    @Column(name = "TaskReviewRequestedAt")
    private LocalDateTime taskReviewRequestedAt;

    @Column(name = "TaskCompletedAt")
    private Date taskCompletedAt;

    @PrePersist
    void prePersist() {
        if (taskCreatedAt == null) {
            taskCreatedAt = LocalDateTime.now();
        }
    }
}