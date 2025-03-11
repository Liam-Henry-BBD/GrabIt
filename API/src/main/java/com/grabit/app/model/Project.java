package com.grabit.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer projectID;
    @Getter
    @Setter
    private String projectName;
    @Getter
    @Setter
    private String projectDescription;
    @Getter
    @Setter
    private Date createdAt;
    @Getter
    @Setter
    private Date updatedAt;
    @Setter
    @Getter
    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;
}
