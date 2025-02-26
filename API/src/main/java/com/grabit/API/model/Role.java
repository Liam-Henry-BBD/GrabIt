package com.grabit.api.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Roles", schema = "grabit")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Byte roleId;

    @Column(name = "RoleTitle", nullable = false, length = 50)
    private String roleTitle;
}
