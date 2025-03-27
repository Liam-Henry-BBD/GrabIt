package com.grabit.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = "Something went wrong!";

        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        switch (statusCode) {
            case 404 -> message = "Resource not found!";
            case 403 -> message = "Access denied!";
            case 500 -> message = "Internal server error!";
            case 401 -> message = "Unauthorized!";
        }

        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
    }

    public record ErrorResponse(int status, String message) {
    }
}
