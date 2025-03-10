package com.grabit.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateResponseDTO {
    private String message;
    private Integer status;
}
