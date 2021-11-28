package com.epam.turik.restchat.model.objects.user;

import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Optional;

@Data
public class UserUpdate {
    private Optional<String> username;
    private Optional<UserStatus> status;
    private Optional<String> email;
    private Optional<String> timezone;
    private Optional<String> language;
    private Optional<Timestamp> deletionDate;
    private Optional<ChatPermission> chatPermission;
    private Optional<String> ip;
}
