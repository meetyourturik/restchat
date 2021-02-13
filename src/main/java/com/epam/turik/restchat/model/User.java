package com.epam.turik.restchat.model;

public class User {

    private final Long id;
    private final String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
