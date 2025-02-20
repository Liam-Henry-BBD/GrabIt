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

    // @ManyToOne
    // @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID", nullable = false)
    // private Project project;

    @ManyToOne
    @JoinColumn(name = "TaskPointID", referencedColumnName = "TaskPointID", nullable = false)
    private TaskPoint taskPoint;

    @ManyToOne
    @JoinColumn(name = "TaskStatusID", referencedColumnName = "TaskStatusID", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "TaskName", nullable = false, length = 50)
    private String taskName;

    @Column(name = "TaskDescription", nullable = false, length = 255)
    private String taskDescription;

    @Column(name = "TaskDeadline")
    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDate taskDeadline;

    @Column(name = "TaskCreatedAt", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    private Date taskCreatedAt;

    @Column(name = "TaskUpdatedAt")
    // @Temporal(TemporalType.TIMESTAMP)
    private Date taskUpdatedAt;

    @Column(name = "TaskReviewRequestedAt")
    // @Temporal(TemporalType.TIMESTAMP)
    private Date taskReviewRequestedAt;

    @Column(name = "TaskCompletedAt")
    // @Temporal(TemporalType.TIMESTAMP)
    private Date taskCompletedAt;

}