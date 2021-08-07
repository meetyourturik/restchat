package com.epam.turik.restchat.rest.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String status;
    private String email;
    private String timezone;
    private String language;
    private String deletionDate;
    private String chatPermission;
    private String ip;
}
