package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.rest.objects.ExceptionDTO;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ExceptionDTO handle(HttpServletRequest request, Exception e) {
        return new ExceptionDTO(e.getMessage(), "121");
    }
}
