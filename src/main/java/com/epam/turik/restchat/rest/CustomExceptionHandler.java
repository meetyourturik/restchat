package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // wrong enum
    @ExceptionHandler(value = BindException.class)
    public ExceptionDTO handleEnum(BindException ex, HttpServletRequest request) {
        return new ExceptionDTO("BindException","Wrong ENUM parameter in request", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ExceptionDTO handleUserNotFound() {
        // разобраться, когда какие колько параметров нужно https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
        return new ExceptionDTO("UserNotFoundException","can't find user with id", HttpStatus.NOT_FOUND.value());
    }

    /*
    @ExceptionHandler(value = Exception.class)
    public ExceptionDTO handleDefault(HttpServletRequest request, Exception e) {
        return new ExceptionDTO("Default Exception","something went wrong, dunno what tho", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    */
}
