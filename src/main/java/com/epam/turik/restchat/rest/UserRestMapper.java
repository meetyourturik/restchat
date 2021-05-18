package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public abstract class UserRestMapper {
    abstract UserDTO toDTO(User user);

    abstract User fromDTO(UserDTO userDTO);

    Timestamp timestampToString(String str) {
        return Timestamp.valueOf(str);
    }
}
