package com.grabit.app.exceptions;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Unauthorized extends ResponseStatusException {
    public Unauthorized(String message){
        super(HttpStatus.UNAUTHORIZED, message);
    }
}