package com.grabit.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectAndRoleDTO {
    private Integer projectID;
    private String projectName;
    private String projectDescription;
    private Integer projectCollaboratorID;
    private Byte collaboratorRole;
    private Boolean isActive;
}
