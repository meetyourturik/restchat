package com.epam.turik.restchat.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class InvalidEnumFieldException extends ProblemException {
    private final List<Violation> violations = new ArrayList<>();
    private final String detail;

    public InvalidEnumFieldException(String className, List<FieldError> errors) {
        this.detail = "Invalid enum field for " + className;
        for (FieldError error : errors) {
            Violation violation = new Violation();
            violation.setCode("invalid_enum_field");
            violation.setField(error.getField());
            violation.setDetail(error.getRejectedValue().toString());
            violations.add(violation);
        }
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
        return violations;
    }
}
