package com.grabit.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TaskPoints", schema = "grabit")
public class TaskPoint {
    @Id
    @Column(name = "TaskPointID", nullable = false)
    private byte taskPointID;

    @Column(name = "TaskDifficulty", nullable = false, unique = true, length = 20)
    private String taskDifficulty;

}