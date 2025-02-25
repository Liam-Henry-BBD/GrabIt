package com.grabit.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TaskStatus", schema = "grabit")
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskStatusID", nullable = false)
    private byte taskStatusID;

    @Column(name = "StatusName", nullable = false, length = 50)
    private String statusName;
}