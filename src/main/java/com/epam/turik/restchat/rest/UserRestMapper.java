package com.epam.turik.restchat.rest;

/**
 * maybe this should be generic as well
 * also gotta validate first
 */

import com.epam.turik.restchat.model.objects.user.ChatPermission;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserStatus;
import com.epam.turik.restchat.rest.objects.UserDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
public class UserRestMapper {
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getStatus().name(),
                user.getEmail(),
                user.getTimezone(),
                user.getLanguage(),
                user.getDeletionDate().toString(),
                user.getChatPermission().name(),
                user.getIp()
        );
    }

    @SneakyThrows
    public User convertToBusinessObject(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                UserStatus.valueOf(userDTO.getStatus()),
                userDTO.getEmail(),
                userDTO.getTimezone(),
                userDTO.getLanguage(),
                Timestamp.valueOf(userDTO.getDeletionDate()),
                ChatPermission.valueOf(userDTO.getChatPermission()),
                userDTO.getIp()
        );
    }
}
