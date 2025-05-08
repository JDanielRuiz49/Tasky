package com.tasks.task.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestFormatInvalid extends RuntimeException {
    public RequestFormatInvalid(String message) {
        super(message);
    }
}
