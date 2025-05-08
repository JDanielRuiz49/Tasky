package com.tasks.task.service.exceptions;

import com.tasks.task.model.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Error> handlerDocumentAlreadyExist(UserAlreadyExists userAlreadyExists){
        Error errorResponse = new Error();
        errorResponse.setMessage(userAlreadyExists.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequestFormatInvalid.class)
    public ResponseEntity<Error> handlerRequestFormatInvalid(RequestFormatInvalid requestFormatInvalid){
        Error errorResponse = new Error();
        errorResponse.setMessage(requestFormatInvalid.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Error> handlerUserNotFound(UserNotFound userNotFound){
        Error errorResponse = new Error();
        errorResponse.setMessage(userNotFound.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Error> handleTaskNotFound(TaskNotFoundException ex) {
        Error errorResponse = new Error();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Maneja errores de validación como campos vacíos (En el caso de titulo y descripción de task)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleBadRequest(IllegalArgumentException ex) {
        Error errorResponse = new Error();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public ResponseEntity<Error> handleConversionException(Exception ex) {
        Error errorResponse = new Error();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse,errorResponse.getCode());
    }
}
