package com.epam.turik.restchat.model.objects.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private long id;
    private String username;
    private UserStatus status;
    private String email;
    private String timezone;
    private String language;
    private Timestamp deletionDate;
    private ChatPermission chatPermission;
    private String ip;

}
