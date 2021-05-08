package com.epam.turik.restchat.model;

/**
 * maybe this should be generic
 */

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.springframework.stereotype.Service;

@Service
public class UserModelMapper {
    public UserEntity convertToEntity(User user) {
        // here we somehow turn user into entity
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getStatus(),
                user.getEmail(),
                user.getTimezone(),
                user.getLanguage(),
                user.getDeletionDate(),
                user.getChatPermission(),
                new Inet(user.getIp())
        );
    }
    public User convertToBusinessObject(UserEntity userEntity) {
        // here we somehow turn it back
        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getStatus(),
                userEntity.getEmail(),
                userEntity.getTimezone(),
                userEntity.getLanguage(),
                userEntity.getDeletionDate(),
                userEntity.getChatPermission(),
                userEntity.getIp().getAddress()
        );
    }
}
