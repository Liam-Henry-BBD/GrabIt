package com.grabit.API.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectLeaderboardDTO {
    private Integer position; 
    private Integer userId;
    private String githubId;
    private Integer totalScore;
}
