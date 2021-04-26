package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String status;
}
