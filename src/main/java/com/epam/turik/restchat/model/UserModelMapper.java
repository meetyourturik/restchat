package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserEntity toEntity(User user);

    User fromEntity(UserEntity userEntity);

    List<UserEntity> toEntityList(List<User> users);

    List<User> fromEntityList(List<UserEntity> userEntity);

    default String inetToString(Inet inet) {
        return inet.getAddress();
    }

    default Inet toInet(String string) {
        return new Inet(string);
    }
}
