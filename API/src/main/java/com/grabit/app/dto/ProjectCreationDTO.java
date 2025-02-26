package com.grabit.app.dto;

import com.grabit.app.model.Project;
import com.grabit.app.model.ProjectCollaborator;

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
