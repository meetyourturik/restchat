package com.epam.turik.restchat.model.objects.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private UserStatus status;
    private String email;
    private String timezone;
    private String language;
    private Timestamp deletionDate;
    private ChatPermission chatPermission;
    private String ip;

    public User(String username, UserStatus status, String email, String timezone, String language, Timestamp deletionDate, ChatPermission chatPermission, String ip) {
        this.username = username;
        this.status = status;
        this.email = email;
        this.timezone = timezone;
        this.language = language;
        this.deletionDate = deletionDate;
        this.chatPermission = chatPermission;
        this.ip = ip;
    }
}
