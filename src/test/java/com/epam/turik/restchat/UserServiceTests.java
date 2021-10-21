package com.epam.turik.restchat;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.PatchService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    @Mock
    UserRepository userRepository;
    @Mock
    UserModelMapper userModelMapper;
    @Mock
    PatchService patchService;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("should return user by id")
    void getUserByIdTest() {
        // given - Готовим тестовые данные
        long id = 1l;
        UserEntity userEntity = mock(UserEntity.class);
        userEntity.setUsername("turik");
        userEntity.setId(id);
        // ... other fields
        User user = new User();
        user.setUsername("turik");
        user.setId(id);
        // when
        when(userRepository.findUserById(eq(id))).thenReturn(Optional.of(userEntity));
        when(userModelMapper.fromEntity(eq(userEntity))).thenReturn(user);
        // then
        User result = userService.getUserById(id);

        assertEquals(result, user);
        verify(userRepository, times(1)).findUserById(eq(id));
        verify(userModelMapper, times(1)).fromEntity(eq(userEntity));
    }
}
