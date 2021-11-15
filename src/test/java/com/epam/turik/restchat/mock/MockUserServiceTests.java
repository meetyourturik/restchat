package com.epam.turik.restchat.mock;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.UpdateService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;
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
        when(userModelMapper.fromEntity(any(UserEntity.class))).thenReturn(user);
        // then
        User result = userService.getUserById(id);

        assertEquals(result, user);
        verify(userRepository, times(1)).findUserById(id);
        verify(userModelMapper, times(1)).fromEntity(any(UserEntity.class));
    }

    @Test
    @DisplayName("should return all users that were added")
    void getSomeUsers() {
        // given
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUsername("turik");
        users.add(user);
        //when
        when(userModelMapper.fromEntityList(anyList())).thenReturn(users);
        // then
        List<User> result = userService.getAllUsers();
        assertEquals(result, users);

        verify(userRepository, times(1)).findAll();
        verify(userModelMapper, times(1)).fromEntityList(anyList());
    }

    @Test
    @DisplayName("should find users by filter with all parameters")
    void getUsersByAllParams() {
        // given
        UserFilter filter = new UserFilter();
        filter.setUsername("mo");
        filter.setLanguage("en");
        filter.setStatus(UserStatus.ACTIVE);
        filter.setChatPermission(ChatPermission.FRIENDS_ONLY);

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUsername("turik");
        users.add(user);
        //when
        when(userModelMapper.fromEntityList(anyList())).thenReturn(users);

        // then
        List<User> result = userService.getUsersByFilter(filter);
        assertEquals(result, users);

        verify(userRepository, times(1)).findAll(any(Example.class));
        verify(userModelMapper, times(1)).fromEntityList(anyList());
    }

    @Test
    @DisplayName("should successfully update fields of User")
    void updateTimestampFields() {
        // given
        long id = 1L;
        UserUpdate patch = new UserUpdate();
        patch.setStatus(Optional.empty());
        // when
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(userModelMapper.fromEntity(any(UserEntity.class))).thenReturn(new User());
        when(userModelMapper.toEntity(any(User.class))).thenReturn(new UserEntity());
        // then
        User result = userService.updateUser(id, patch);
        verify(updateService, times(1)).applyUpdate(any(User.class), any(UserUpdate.class));
        verify(userModelMapper, times(1)).toEntity(any(User.class));
        verify(userRepository, times(1)).findUserById(anyLong());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userModelMapper, times(1)).fromEntity(any(UserEntity.class));
    }

    @Test
    @DisplayName("should successfully delete existing user")
    void deleteExistingUser() {
        // given
        // when
        userService.deleteUser(anyLong());
        // then
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}
