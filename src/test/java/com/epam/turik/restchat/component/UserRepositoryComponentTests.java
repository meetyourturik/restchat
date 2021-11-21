package com.epam.turik.restchat.component;

import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.infrastructure.ComponentTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ComponentTest
class UserRepositoryComponentTests {
    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
        assertNotNull(userRepository);
    }
}
