package com.epam.turik.restchat;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.PatchService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@Slf4j
public class FakeUserServiceTest {
    private UserService getUserService(UserRepository repository) {
        return new UserService(repository, Mappers.getMapper( UserModelMapper.class ), new PatchService(new ObjectMapper()));
    }

    @Test
    @DisplayName("should return user by id")
    public void getUserByIdTest() {
        UserRepository repository = new FakeUserRepository();
        UserService userService = getUserService(repository);
        // given - Готовим тестовые данные
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("john doe");
        // when
        long id = repository.save(userEntity).getId();

        // then
        User result = userService.getUserById(id);

        Assertions.assertEquals(result.getUsername(), userEntity.getUsername());
    }
}
