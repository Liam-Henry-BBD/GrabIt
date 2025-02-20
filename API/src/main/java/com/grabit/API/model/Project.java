package com.grabit.API.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProjectId;
    @Getter
    @Setter
    private String ProjectName;
    @Getter
    @Setter
    private String ProjectDescription;
    @Getter
    @Setter
    private Date CreatedAt;
    @Getter
    @Setter
    private Date UpdatedAt;
}
