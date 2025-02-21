package com.grabit.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
// import com.grabit.API.model.User;
// import com.grabit.API.model.Role;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ProjectCollaborators", schema = "grabit")
public class ProjectCollaboratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectCollaboratorID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    // @ManyToOne
    // @JoinColumn(name = "UserID", nullable = false)
    // private User user;

    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @Column(name = "RoleID", nullable = false)
    private Byte roleID;

    // @ManyToOne
    // @JoinColumn(name = "RoleID", nullable = false)
    // private Role role;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    // Lombok will generate getters and setters
}