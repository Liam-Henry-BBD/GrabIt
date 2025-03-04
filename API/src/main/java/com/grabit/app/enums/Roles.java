package com.grabit.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
    PROJECT_LEAD((byte) 1),
    PROJECT_MEMBER((byte) 2),
    TASK_GRABBER((byte) 3),
    TASK_COLLABORATOR((byte) 4);

    private final byte role;

}
