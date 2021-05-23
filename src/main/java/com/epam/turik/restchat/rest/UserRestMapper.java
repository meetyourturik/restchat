package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> user);

    List<User> fromDTOList(List<UserDTO> userDTO);

    default Timestamp timestampToString(String str) {
        return Timestamp.valueOf(str);
    }
}
