package com.grabit.API.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Tasks", schema = "grabit")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID", nullable = false)
    private int taskID;

    @ManyToOne
//    @NotBlank(message = "Project ID is required.")
    @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
//    @NotBlank(message = "Task point is required.")
    @JoinColumn(name = "TaskPointID", referencedColumnName = "TaskPointID", nullable = false)
    private TaskPoint taskPoint;

    @ManyToOne
//    @NotBlank(message = "Task status is required.")
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
    private Date taskCreatedAt;

    @Column(name = "TaskUpdatedAt")
    private Date taskUpdatedAt;

    @Column(name = "TaskReviewRequestedAt")
    private Date taskReviewRequestedAt;

    @Column(name = "TaskCompletedAt")
    private Date taskCompletedAt;

}