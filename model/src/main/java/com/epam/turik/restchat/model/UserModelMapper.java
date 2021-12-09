package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.model.objects.user.UserFilter;
import com.vladmihalcea.hibernate.type.basic.Inet;
import org.mapstruct.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserModelMapper {

    UserEntity toEntity(User user);

    User fromEntity(UserEntity userEntity);

    List<UserEntity> toEntityList(List<User> users);

    List<User> fromEntityList(List<UserEntity> userEntity);

    @Mapping(target = "id", ignore = true)
    void updateUser(UserUpdate update, @MappingTarget User user);

    default Example<UserEntity> filterToExample(UserFilter filter) {
        UserEntity exampleEntity = new UserEntity();

        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        if (filter.getUsername() != null) {
            exampleEntity.setUsername(filter.getUsername());
            matcher = matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());
        }

        if (filter.getLanguage() != null) {
            exampleEntity.setLanguage(filter.getLanguage());
            matcher = matcher.withMatcher("language", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());
        }

        if (filter.getStatus() != null) {
            exampleEntity.setStatus(filter.getStatus());
            matcher = matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        if (filter.getChatPermission() != null) {
            exampleEntity.setChatPermission(filter.getChatPermission());
            matcher = matcher.withMatcher("chatPermission", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        return Example.of(exampleEntity, matcher);
    }

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
