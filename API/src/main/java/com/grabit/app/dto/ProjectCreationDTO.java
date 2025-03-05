package com.grabit.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectCreationDTO {
    private String projectName;
    private String projectDescription;
}
