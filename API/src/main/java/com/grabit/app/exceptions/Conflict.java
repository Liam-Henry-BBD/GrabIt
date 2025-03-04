package com.grabit.app.exceptions;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class Conflict extends ResponseStatusException {
    public Conflict(String message){
        super(HttpStatus.CONFLICT, message);
    }
}