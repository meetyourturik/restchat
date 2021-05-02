package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserModelMapper {
    public UserEntity convertToEntity(User user) {
        return new UserEntity();
    }
    public User convertToBusinessObject(UserEntity userEntity) {
        return new User();
    }
}
