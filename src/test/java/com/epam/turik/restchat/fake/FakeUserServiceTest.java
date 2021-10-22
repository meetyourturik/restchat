package com.epam.turik.restchat.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.PatchService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.*;
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
        private List<UserEntity> entities;

        @BeforeEach
        private void initEntities() {
            entities = new ArrayList<>();

            UserEntity entity1 = new UserEntity();
            entity1.setUsername("john galt");
            entity1.setLanguage("albanian");
            entity1.setStatus(UserStatus.ACTIVE);
            entity1.setChatPermission(ChatPermission.EVERYONE);

            UserEntity entity2 = new UserEntity();
            entity2.setUsername("john doe");
            entity2.setLanguage("algerian");
            entity2.setStatus(UserStatus.ACTIVE);
            entity2.setChatPermission(ChatPermission.EVERYONE);

            UserEntity entity3 = new UserEntity();
            entity3.setUsername("richard roe");
            entity3.setLanguage("algerian");
            entity3.setStatus(UserStatus.INACTIVE);
            entity3.setChatPermission(ChatPermission.EVERYONE);

            UserEntity entity4 = new UserEntity();
            entity4.setUsername("monty python");
            entity4.setLanguage("english");
            entity4.setStatus(UserStatus.ACTIVE);
            entity4.setChatPermission(ChatPermission.FRIENDS_ONLY);

            UserEntity entity5 = new UserEntity();
            entity5.setUsername("moe nyxon");
            entity5.setLanguage("enigmatic");
            entity5.setStatus(UserStatus.INACTIVE);
            entity5.setChatPermission(ChatPermission.FRIENDS_ONLY);

            entities.add(entity1);
            entities.add(entity2);
            entities.add(entity3);
            entities.add(entity4);
            entities.add(entity5);
        }

        @Test
        @DisplayName("should find users by username pattern")
        public void getUsersByUsername() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setUsername("john");
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should find users by language pattern")
        public void getUsersByLanguage() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setLanguage("al");
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(3, result.size());
        }

        @Test
        @DisplayName("should find users by status pattern")
        public void getUsersByStatus() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setStatus(UserStatus.INACTIVE);
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should find users by chatPermission pattern")
        public void getUsersByChatPermission() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setChatPermission(ChatPermission.FRIENDS_ONLY);
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should find users by username and language pattern")
        public void getUsersByUsernameAndLanguage() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setUsername("john");
            filter.setLanguage("alg");
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("should find users by username, language and status pattern")
        public void getUsersByUsernameAndLanguageAndStatus() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setUsername("mo");
            filter.setLanguage("en");
            filter.setStatus(UserStatus.ACTIVE);
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("should find users by filter with all parameters")
        public void getUsersByAllParams() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setUsername("mo");
            filter.setLanguage("en");
            filter.setStatus(UserStatus.ACTIVE);
            filter.setChatPermission(ChatPermission.FRIENDS_ONLY);
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(1, result.size());
        }

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
