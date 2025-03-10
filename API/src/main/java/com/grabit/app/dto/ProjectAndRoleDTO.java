package com.grabit.app.dto;

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
    private Byte collaboratorRole;
}
