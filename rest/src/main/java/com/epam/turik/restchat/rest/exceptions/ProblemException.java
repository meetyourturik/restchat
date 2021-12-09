package com.epam.turik.restchat.rest.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class ProblemException extends RuntimeException {
    public abstract String getType();
    public abstract String getTitle();
    public abstract String getDetail();
    public abstract HttpStatus getStatus();
    public abstract List<Violation> getViolations();
}
