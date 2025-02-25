package com.grabit.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


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
    private Integer taskCollaboratorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;  // Assuming the User entity exists with the appropriate mapping

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;  // Assuming the Role entity exists with the appropriate mapping

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;  // Assuming the Task entity exists with the appropriate mapping

    @Column(name = "JoinedAt", nullable = false)
    private LocalDate joinedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
}
