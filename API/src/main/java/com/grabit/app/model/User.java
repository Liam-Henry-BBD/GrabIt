package com.grabit.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Users", schema = "grabit")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "GitHubID", nullable = false, unique = true, length = 100)
    private String gitHubID;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDate joinedAt;
}
