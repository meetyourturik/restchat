package com.epam.turik.restchat.rest.exceptions;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class EntityNotFoundException extends ProblemException {
    private final List<Violation> violations = new ArrayList<>();
    public EntityNotFoundException(String field, Long value) {
        Violation violation = new Violation();
        violation.setCode("entity_not_found");
        violation.setField(field);
        violation.setDetail("Entity with " + field + " = " + value + " is not found");
        violations.add(violation);
    }

    @Override
    public String getType() {
        return "http://example.com/some-documentation#entity-not-found";
    }

    @Override
    public String getTitle() {
        return "Entity not found";
    }

    @Override
    public String getDetail() {
        return "Entity not found";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public List<Violation> getViolations() {
        return violations;
    }
}
