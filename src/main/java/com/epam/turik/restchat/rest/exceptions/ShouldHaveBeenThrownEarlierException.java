package com.epam.turik.restchat.rest.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ShouldHaveBeenThrownEarlierException extends ProblemException {
    private final String detail;

    public ShouldHaveBeenThrownEarlierException(String detail) {
        this.detail = detail;
    }

    @Override
    public String getType() {
        return "http://example.com/some-documentation#bad-request";
    }

    @Override
    public String getTitle() {
        return "Bad request";
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<Violation> getViolations() {
        return null;
    }
}
