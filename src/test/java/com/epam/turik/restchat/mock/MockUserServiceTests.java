package com.epam.turik.restchat.mock;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.UpdateService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class MockUserServiceTests {
    @Mock
    UserRepository userRepository;
    @Mock
    UserModelMapper userModelMapper;
    @Mock
    UpdateService updateService;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("should create user")
    void creatingUser() {
        // given
        long id = 1L;
        UserEntity userEntity = mock(UserEntity.class);
        userEntity.setUsername("turik");
        userEntity.setId(id);
        // ... other fields
        User user = new User();
        user.setUsername("turik");
        user.setId(id);
        // when
        /*
        UserEntity userEntity = userModelMapper.toEntity(user);
        UserEntity returnUser = userRepository.save(userEntity);
        return userModelMapper.fromEntity(returnUser);
         */
        when(userModelMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userModelMapper.fromEntity(userEntity)).thenReturn(user);
        // then
        User result = userService.createUser(user);
        assertEquals(result, user);
        verify(userModelMapper, times(1)).toEntity(user);
        verify(userModelMapper, times(1)).fromEntity(userEntity);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("should return user by id")
    void getUserByIdTest() {
        // given - Готовим тестовые данные
        long id = 1L;
        UserEntity userEntity = mock(UserEntity.class);
        userEntity.setUsername("turik");
        userEntity.setId(id);
        // ... other fields
        User user = new User();
        user.setUsername("turik");
        user.setId(id);
        // when
    /* sonarlint:
       the default matching behavior (i.e. without argument matchers) uses equals().
       If only the matcher eq() is used, the call is equivalent to the call without matchers,
       i.e. the eq() is not necessary and can be omitted
     */
        when(userRepository.findUserById(id)).thenReturn(Optional.of(userEntity));
        when(userModelMapper.fromEntity(userEntity)).thenReturn(user);
        // then
        User result = userService.getUserById(id);

        assertEquals(result, user);
        verify(userRepository, times(1)).findUserById(id);
        verify(userModelMapper, times(1)).fromEntity(userEntity);
    }


    @Nested
    @DisplayName("getAllUsers tests")
    class GetAllUsersUsersTest {
        @Test
        @DisplayName("should return empty list when no users")
        void getZeroUsersTest() {

        }

        @Test
        @DisplayName("should return all users that were added")
        void getSomeUsers() {

        }
    }

    @Nested
    @DisplayName("getUsersByFilter tests")
    class GetUsersByFilterTest {
        @Test
        @DisplayName("should find users by username pattern")
        void getUsersByUsername() {

        }

        @Test
        @DisplayName("should find users by language pattern")
        void getUsersByLanguage() {

        }

        @Test
        @DisplayName("should find users by status pattern")
        void getUsersByStatus() {

        }

        @Test
        @DisplayName("should find users by chatPermission pattern")
        void getUsersByChatPermission() {

        }

        @Test
        @DisplayName("should find users by username and language pattern")
        void getUsersByUsernameAndLanguage() {

        }

        @Test
        @DisplayName("should find users by username, language and status pattern")
        void getUsersByUsernameAndLanguageAndStatus() {

        }

        @Test
        @DisplayName("should find users by filter with all parameters")
        void getUsersByAllParams() {

        }

        @Test
        @DisplayName("should return empty list when no users found")
        void getZeroUsers() {

        }
    }

    @Nested
    @DisplayName("updateUser tests")
    class UpdateUserTest {
        @Test
        @DisplayName("should successfully update String fields of User")
        void updateStringFields() {

        }

        @Test
        @DisplayName("should successfully update Enum fields of User")
        void updateEnumFields() {

        }

        @Test
        @DisplayName("should successfully update Timestamp fields of User")
        void updateTimestampFields() {

        }

        @Test
        @DisplayName("should successfully remove field")
        void removeField() {

        }
    }

    @Nested
    @DisplayName("deleteUser tests")
    class DeleteUserTest {
        @Test
        @DisplayName("should successfully delete existing user")
        void deleteExistingUser() {

        }

        @Test
        @DisplayName("should do nothing when deleting nonexistent user")
        void deleteNonExistingUser() {

        }
    }
}
