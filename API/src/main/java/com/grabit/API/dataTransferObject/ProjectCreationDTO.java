package com.grabit.API.dataTransferObject;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaborator;

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
