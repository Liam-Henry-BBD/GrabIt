package com.grabit.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TaskCollaborators", schema = "grabit")
public class TaskCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskCollaboratorID", nullable = false)
    private Integer taskCollaboratorID;

    @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID", nullable = false)
    @ManyToOne
    private Role role;

    @JoinColumn(name = "TaskID", referencedColumnName = "TaskID", nullable = false)
    @ManyToOne
    private Task task;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
}
