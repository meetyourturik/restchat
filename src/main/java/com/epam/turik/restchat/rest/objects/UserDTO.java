package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String timezone;
    private String language;
    private String userStatus;
    private String status;
    private String deletionDate;
    private String chatPermission;
    private String ip;
}
