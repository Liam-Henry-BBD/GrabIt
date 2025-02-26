package com.grabit.api.dto;

import com.grabit.api.model.Project;
import com.grabit.api.model.ProjectCollaborator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectCreationDTO {
    private Project project;
    private ProjectCollaborator projectCollaborator;
}
