package com.epam.turik.restchat.model.objects.user;

import java.sql.Timestamp;

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
