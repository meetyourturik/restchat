package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserModelMapper {

    UserEntity toEntity(User user);

    User fromEntity(UserEntity userEntity);

    List<UserEntity> toEntityList(List<User> users);

    List<User> fromEntityList(List<UserEntity> userEntity);

    // https://stackoverflow.com/questions/58313368/use-java-8-optional-with-mapstruct
    // https://stackoverflow.com/questions/49067870/how-can-i-map-properties-conditionally-with-mapstruct-1-2/

    @Mapping(target = "id", ignore = true)
    void updateUser(UserUpdate update, @MappingTarget User user);

    default <T> T optionalToValue(Optional<T> optional) {
        return optional.orElse(null);
    }

    default String inetToString(Inet inet) {
        return inet == null ? null : inet.getAddress();
    }

    default Inet toInet(String string) {
        return string == null ? null : new Inet(string);
    }
}
