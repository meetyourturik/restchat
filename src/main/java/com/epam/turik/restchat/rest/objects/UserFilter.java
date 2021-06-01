package com.epam.turik.restchat.rest.objects;

import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.Data;

@Data
public class UserFilter {
    private String username;
    private String language;
    private UserStatus status;
    private ChatPermission chatPermission;
}
