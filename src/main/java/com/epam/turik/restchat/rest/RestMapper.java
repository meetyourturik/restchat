package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class RestMapper {
    public UserDTO transformUser(User user) {
        return new UserDTO();
    }
}
