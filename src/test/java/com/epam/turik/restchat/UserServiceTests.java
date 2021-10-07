package com.epam.turik.restchat;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.model.PatchService;
import com.epam.turik.restchat.model.UserModelMapper;
import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest(properties="spring.main.lazy-initialization=true")
public class UserServiceTests {
    @Mock
    UserRepository userRepository;
    @Mock
    UserModelMapper userModelMapper;
    @Mock
    PatchService patchService;

    UserService userService = new UserService(userRepository, userModelMapper, patchService);

    @Test
    public void getUserByIdTest() {
        UserEntity userEntity = new UserEntity();
        Mockito.when(userRepository.findUserById(1l)).thenReturn(Optional.of(userEntity));
        User user = new User();
        user.setId(1l);
        Mockito.when(userModelMapper.fromEntity(userEntity)).thenReturn(user);
        User user1 = userService.getUserById(1l);
        System.out.println(user1);
        Assert.assertEquals(user, user);
        Assert.assertEquals((long) user.getId(), 1l);

    }

}
