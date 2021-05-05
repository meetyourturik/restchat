package com.epam.turik.restchat.model;

/**
 * maybe this should be generic
 */

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserModelMapper {
    public UserEntity convertToEntity(User user) {
        // here we somehow turn user into entity
        return new UserEntity();
    }
    public User convertToBusinessObject(UserEntity userEntity) {
        // here we somehow turn it back
        return new User();
    }
}
