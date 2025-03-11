package com.grabit.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Roles", schema = "grabit")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Byte roleID;

    @Column(name = "RoleTitle", nullable = false, length = 50)
    private String roleTitle;
}
