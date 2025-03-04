package com.grabit.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    AVAILABLE((byte) 1),
    GRABBED((byte) 2),
    REVIEW((byte) 3),
    COMPLETE((byte) 4);

    private final byte status;

}
