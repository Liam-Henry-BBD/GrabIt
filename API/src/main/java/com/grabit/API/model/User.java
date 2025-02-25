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
@Table(name = "Users", schema = "grabit")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "GitHubID", nullable = false, unique = true, length = 100)
    private String gitHubId;

    @Column(name = "JoinedAt", nullable = false)
    private LocalDate joinedAt;

}
