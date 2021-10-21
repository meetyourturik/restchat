package com.epam.turik.restchat.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.PatchService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DisplayName("UserService tests")
@Execution(ExecutionMode.CONCURRENT)
public class FakeUserServiceTest {
    private UserService getUserService(UserRepository repository) {
        return new UserService(repository, Mappers.getMapper( UserModelMapper.class ), new PatchService(new ObjectMapper()));
    }

    private UserService getUserService() {
        UserRepository repository = new FakeUserRepository();
        return getUserService(repository);
    }

    @Nested
    @DisplayName("createUser tests")
    class CreateUserTest {
        @Test
        @DisplayName("should create user")
        public void creatingUser() {
            UserService userService = getUserService();
            // given
            User user = new User();
            user.setUsername("john doe");
            // when
            // then
            User result = userService.createUser(user);
            assertEquals(result.getUsername(), user.getUsername());
            assertEquals(result.getId(), 1L);
        }
    }

    @Nested
    @DisplayName("getUserById tests")
    class GetUserByIdTest {
        @Test
        @DisplayName("should return user by id")
        public void getUserByIdTest() {
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            // given
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("john doe");
            // when
            long id = repository.save(userEntity).getId();
            // then
            User result = userService.getUserById(id);
            assertEquals(result.getUsername(), userEntity.getUsername());

        }

        @Test
        @DisplayName("should throw UserNotFoundException when no user found")
        public void throwWhenUserNotFoundTest() {
            UserService userService = getUserService();
            // given
            long nonexistentUserId = 3L;
            // when
            // then
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(nonexistentUserId));
        }
    }

    @Nested
    @DisplayName("getAllUsers tests")
    class GetAllUsersUsersTest {
        @Test
        @DisplayName("should return empty list when no users")
        public void getZeroUsersTest() {
            UserService userService = getUserService();
            // given
            // when
            // then
            List<User> users = userService.getAllUsers();
            assertEquals(users.size(), 0);
        }

        @Test
        @DisplayName("should return all users that were added")
        public void getSomeUsers() {
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            // given
            int nUsers = 12;
            List<UserEntity> userEntities = new ArrayList<>();
            List<String> userNames = new ArrayList<>();
            for (int i = 0; i < nUsers; i++) {
                UserEntity entity = new UserEntity();
                String name = "user" + i;
                userNames.add(name);
                entity.setUsername(name);
                userEntities.add(entity);
            }
            // when
            repository.saveAll(userEntities);
            // then
            List<User> result = userService.getAllUsers();
            assertEquals(result.size(), nUsers);
            List<String> resultNames = new ArrayList<>();
            for (User user : result) {
                resultNames.add(user.getUsername());
            }
            assertEquals(resultNames, userNames);
        }
    }

    @Nested
    @DisplayName("getUsersByFilter tests")
    class GetUsersByFilterTest {
        // TODO
    }

    @Nested
    @DisplayName("updateUser tests")
    class UpdateUserTest {
        // TODO
    }

    @Nested
    @DisplayName("deleteUser tests")
    class DeleteUserTest {
        @Test
        @DisplayName("should successfully delete existing user")
        public void deleteExistingUser() {
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            // given
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("john doe");
            UserEntity userEntity2 = new UserEntity();
            userEntity2.setUsername("elijah wood");
            // when
            repository.save(userEntity);
            repository.save(userEntity2);
            // then
            userService.deleteUser(1L);
            assertFalse(repository.existsById(1L));
            assertEquals(1, repository.count());
        }

        @Test
        @DisplayName("should do nothing when deleting nonexistent user")
        public void deleteNonExistingUser() {
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            // given
            long nonexistentUserId = 3L;
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("john doe");
            UserEntity userEntity2 = new UserEntity();
            userEntity2.setUsername("elijah wood");
            // when
            repository.save(userEntity);
            repository.save(userEntity2);
            // then
            assumeFalse(repository.existsById(nonexistentUserId));
            userService.deleteUser(nonexistentUserId);
            assertEquals(2, repository.count());
        }
    }
}
