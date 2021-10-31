package com.epam.turik.restchat.model.objects.user;

import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS") // needed for serializing when patching
    private Timestamp deletionDate;
    private ChatPermission chatPermission;
    private String ip;
}
