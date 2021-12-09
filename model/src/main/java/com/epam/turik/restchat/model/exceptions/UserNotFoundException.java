package com.epam.turik.restchat.model.exceptions;

public class UserNotFoundException extends RuntimeException {
    private final long id;

    public UserNotFoundException(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }
}