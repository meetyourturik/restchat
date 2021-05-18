package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserModelMapper {

    abstract UserEntity toEntity(User user);

    abstract User fromEntity(UserEntity userEntity);

    String inetToString(Inet inet) {
        return inet.getAddress();
    }

    Inet toInet(String string) {
        return new Inet(string);
    }
}
