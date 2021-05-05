package com.epam.turik.restchat.rest;

/**
 * maybe this should be generic as well
 */

import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserRestMapper {
    public UserDTO convertToDTO(User user) {
        // here we somehow turn user into dto
        return new UserDTO();
    }
    public User convertToBusinessObject(UserDTO userDTO) {
        // here we somehow turn it back
        return new User();
    }
}
