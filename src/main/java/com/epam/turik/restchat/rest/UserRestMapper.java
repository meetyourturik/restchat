package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);

    default Timestamp timestampToString(String str) {
        return Timestamp.valueOf(str);
    }
}
