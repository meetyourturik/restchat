package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class UserFilterDTO {
    private String username;
    private String language;
    private String status;
    private String chatPermission;
}
