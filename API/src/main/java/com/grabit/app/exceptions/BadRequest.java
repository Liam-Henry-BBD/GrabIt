package com.grabit.app.exceptions;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends ResponseStatusException {
    public BadRequest(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}