package com.epam.turik.restchat.model.objects.user;

import lombok.Data;

import java.util.Optional;

@Data
public class UserUpdate {
    private Optional<String> username;

}
