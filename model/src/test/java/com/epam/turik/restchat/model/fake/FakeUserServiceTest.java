package com.epam.turik.restchat.model.fake;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.model.objects.user.UserFilter;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@DisplayName("UserService tests")
@Execution(ExecutionMode.CONCURRENT)
public class FakeUserServiceTest {
    private UserService getUserService(UserRepository repository) {
        return new UserService(repository, Mappers.getMapper( UserModelMapper.class ));
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
        void creatingUser() {
            UserService userService = getUserService();
            // given
            User user = new User();
            user.setUsername("john doe");
            // when
            // then
            User result = userService.createUser(user);
            assertEquals(user.getUsername(), result.getUsername());
            assertEquals(1L, result.getId());
        }
    }

    @Nested
    @DisplayName("getUserById tests")
    class GetUserByIdTest {
        @Test
        @DisplayName("should return user by id")
        void getUserByIdTest() {
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            // given
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("john doe");
            // when
            long id = repository.save(userEntity).getId();
            // then
            User result = userService.getUserById(id);
            assertEquals(userEntity.getUsername(), result.getUsername());

        }

        @Test
        @DisplayName("should throw UserNotFoundException when no user found")
        void throwWhenUserNotFoundTest() {
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
        void getZeroUsersTest() {
            UserService userService = getUserService();
            // given
            // when
            // then
            List<User> users = userService.getAllUsers();
            assertTrue(users.isEmpty());
        }

        @Test
        @DisplayName("should return all users that were added")
        void getSomeUsers() {
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
            assertEquals(userNames, resultNames);
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
        void getUsersByUsername() {
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
        void getUsersByLanguage() {
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
        void getUsersByStatus() {
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
        void getUsersByChatPermission() {
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
        void getUsersByUsernameAndLanguage() {
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
        void getUsersByUsernameAndLanguageAndStatus() {
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
        void getUsersByAllParams() {
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

        @Test
        @DisplayName("should return empty list when no users found")
        void getZeroUsers() {
            // given
            UserRepository repository = new FakeUserRepository();
            UserService userService = getUserService(repository);
            UserFilter filter = new UserFilter();
            filter.setUsername("wonder");
            // when
            repository.saveAll(entities);
            // then
            List<User> result = userService.getUsersByFilter(filter);
            assertEquals(0, result.size());
        }
    }

    @Nested
    @DisplayName("updateUser tests")
    class UpdateUserTest {
        private UserService userService;
        private Long userId;
        private UserUpdate patch;

        @BeforeEach
        private void init() {
            userService = getUserService();
            User user = new User();
            user.setUsername("before");
            user.setStatus(UserStatus.ACTIVE);
            user.setEmail("before@email.com");
            user.setTimezone("EU");
            user.setLanguage("russian");
            user.setDeletionDate(Timestamp.valueOf("2012-12-21 13:17:49.012"));
            user.setChatPermission(ChatPermission.EVERYONE);
            user.setIp("10.0.0.1");
            user = userService.createUser(user);
            userId = user.getId();
            patch = new UserUpdate();
        }

        @Test
        @DisplayName("should successfully update String fields of User")
        void updateStringFields() {
            //given
            patch.setUsername(Optional.of("after"));
            patch.setEmail(Optional.of("after@email.com"));
            patch.setTimezone(Optional.of("EN"));
            patch.setLanguage(Optional.of("english"));
            patch.setIp(Optional.of("192.168.0.1"));
            // when
            User updatedUser = userService.updateUser(userId, patch);
            // then
            User userFromDb = userService.getUserById(userId);
            assertEquals(userFromDb.getUsername(), updatedUser.getUsername());
            assertEquals(userFromDb.getEmail(), updatedUser.getEmail());
            assertEquals(userFromDb.getTimezone(), updatedUser.getTimezone());
            assertEquals(userFromDb.getLanguage(), updatedUser.getLanguage());
            assertEquals(userFromDb.getIp(), updatedUser.getIp());
        }

        @Test
        @DisplayName("should successfully update Enum fields of User")
        void updateEnumFields() {
            //given
            patch.setStatus(Optional.of(UserStatus.BANNED));
            patch.setChatPermission(Optional.of(ChatPermission.FRIENDS_ONLY));
            // when
            User updatedUser = userService.updateUser(userId, patch);
            // then
            User userFromDb = userService.getUserById(userId);
            assertEquals(userFromDb.getStatus(), updatedUser.getStatus());
            assertEquals(userFromDb.getChatPermission(), updatedUser.getChatPermission());
        }

        @Test
        @DisplayName("should successfully update Timestamp fields of User")
        void updateTimestampFields() {
            //given
            patch.setDeletionDate(Optional.of(Timestamp.valueOf("2022-01-14 21:00:00.000")));
            // when
            User updatedUser = userService.updateUser(userId, patch);
            // then
            User userFromDb = userService.getUserById(userId);
            assertEquals(userFromDb.getDeletionDate(), updatedUser.getDeletionDate());
        }

        @Test
        @DisplayName("should successfully remove field")
        void removeField() {
            //given
            patch.setStatus(Optional.empty());
            //when
            User updatedUser = userService.updateUser(userId, patch);
            // then
            User userFromDb = userService.getUserById(userId);
            assertNotNull(userFromDb.getUsername());
            assertNull(userFromDb.getStatus());
        }
    }

    @Nested
    @DisplayName("deleteUser tests")
    class DeleteUserTest {
        @Test
        @DisplayName("should successfully delete existing user")
        void deleteExistingUser() {
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
        void deleteNonExistingUser() {
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
