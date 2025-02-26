package com.grabit.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ProjectCollaborators", schema = "grabit")
public class ProjectCollaborator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectCollaboratorID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    @Column(name = "ProjectID", nullable = false)
    private Integer projectID;

    @Column(name = "RoleID", nullable = false)
    private Integer roleID;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

}