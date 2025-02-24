package com.grabit.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TaskCollaborators", schema = "grabit")
public class TaskCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskCollaboratorID")
    private Integer taskCollaboratorID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    @Column(name = "RoleID", nullable = false)
    private Byte roleID;

    @Column(name = "TaskID", nullable = false)
    private Integer taskID;

    @Column(name = "JoinedAt")
    private LocalDateTime joinedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
}
