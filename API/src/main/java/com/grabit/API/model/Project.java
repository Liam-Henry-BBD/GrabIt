package com.grabit.API.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer projectID;
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
