package com.epam.turik.restchat.rest.exceptions;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class InvalidFieldException extends ProblemException {
    private final List<Violation> violations = new ArrayList<>();

    public InvalidFieldException(String field, Object value) {
        Violation violation = new Violation();
        violation.setCode("invalid_field");
        violation.setField(field);
        violation.setDetail("Invalid value '" + value + "' for field '" + field + "'");
        violations.add(violation);
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
        return "Bad request";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<Violation> getViolations() {
        return violations;
    }
}
