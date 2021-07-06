package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // wrong enum
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleEnum(BindException ex) {
        return new ExceptionDTO("BindException","Wrong ENUM parameter in request", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionDTO handleUserNotFound(UserNotFoundException e) {
        return new ExceptionDTO("UserNotFoundException","can't find user with id " + e.getId(), HttpStatus.NOT_FOUND.value());
    }
}
