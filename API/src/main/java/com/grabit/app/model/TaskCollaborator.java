package com.grabit.app.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "TaskCollaborators", schema = "grabit")
public class TaskCollaborator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskCollaboratorID", nullable = false)
    private Integer taskCollaboratorID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TaskID", nullable = false)
    private Task task;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDate joinedAt = LocalDate.now();

    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;

}
