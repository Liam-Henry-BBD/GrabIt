package com.grabit.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "ProjectCollaborators", schema = "grabit")
public class ProjectCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectCollaboratorID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    @Column(name = "ProjectID", nullable = false)
    private Integer projectID;

    @Column(name = "RoleID", nullable = false)
    private Byte roleID;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;
}