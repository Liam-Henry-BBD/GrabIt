package com.grabit.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSearchDTO {
    private String gitHubID;
    private Integer userID;
}
