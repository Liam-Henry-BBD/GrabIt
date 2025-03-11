package com.grabit.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectLeaderboardDTO {
    private Integer position;
    private Integer userID;
    private String githubID;
    private Integer totalScore;
}
