package com.grabit.app.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectAndRoleDTO {
    private Integer projectID;
    private String projectName;
    private String projectDescription;
    private Integer ProjectCollaboratorID;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Byte collaboratorRole;
}
