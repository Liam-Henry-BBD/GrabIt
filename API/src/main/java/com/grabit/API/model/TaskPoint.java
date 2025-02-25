package com.grabit.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "TaskPoints", schema = "grabit")
public class TaskPoint {

    @Id
    @Column(name = "TaskPointID", nullable = false)
    private byte taskPointID;

    @Column(name = "TaskDifficulty", nullable = false, unique = true, length = 20)
    private String taskDifficulty;

}