package com.grabit.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectLeaderboardDTO {
    private Integer position;
    private Integer userID;
    private String githubID;
    private Integer totalScore;
}
