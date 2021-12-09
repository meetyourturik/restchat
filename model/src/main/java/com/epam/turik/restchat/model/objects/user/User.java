package com.epam.turik.restchat.model.objects.user;

import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
