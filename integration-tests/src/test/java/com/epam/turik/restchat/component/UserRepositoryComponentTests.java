package com.epam.turik.restchat.component;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.infrastructure.ComponentTest;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import com.vladmihalcea.hibernate.type.basic.Inet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ComponentTest
class UserRepositoryComponentTests {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    private void init() { // move to dbUnit
        UserEntity user1 = new UserEntity();
        user1.setId(1L); // trying to create user with id
        user1.setUsername("user1");
        user1.setStatus(UserStatus.ACTIVE);
        user1.setEmail("user1@email.com");
        user1.setTimezone("GMT");
        user1.setLanguage("RU");
        user1.setDeletionDate(Timestamp.valueOf("2012-12-21 13:17:49.012"));
        user1.setChatPermission(ChatPermission.FRIENDS_ONLY);
        user1.setIp(new Inet("192.168.0.1"));

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setEmail("user2@email.com");
        user2.setTimezone("CET");
        user2.setLanguage("EN");
        user2.setDeletionDate(Timestamp.valueOf("2014-07-13 22:36:12.711"));
        user2.setChatPermission(ChatPermission.EVERYONE);
        user2.setIp(new Inet("192.168.0.2"));

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    private void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("should return user by id")
    void getUserByIdTest() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L); // trying to create user with id
        user1.setUsername("user1");
        user1.setStatus(UserStatus.ACTIVE);
        user1.setEmail("user1@email.com");
        user1.setTimezone("GMT");
        user1.setLanguage("RU");
        user1.setDeletionDate(Timestamp.valueOf("2012-12-21 13:17:49.012"));
        user1.setChatPermission(ChatPermission.FRIENDS_ONLY);
        user1.setIp(new Inet("192.168.0.1"));

        Optional<UserEntity> user = userRepository.findUserById(1L);
        assertTrue(user.isPresent());
        assertEquals(user1, user.get());
    }

    @Test
    @DisplayName("should return empty Optional when no user exists")
    void getNonExistentUserTest() {
        long nonExistentId = 7L;
        Optional<UserEntity> user = userRepository.findUserById(nonExistentId);
        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("should find users by status")
    void findUsersByStatusTest() {
        UserEntity exampleEntity = new UserEntity();
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        exampleEntity.setStatus(UserStatus.ACTIVE);
        matcher = matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());

        List<UserEntity> users = userRepository.findByExample(Example.of(exampleEntity, matcher));
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("should find zero users by bad example")
    void findZeroByExampleTest() {
        UserEntity exampleEntity = new UserEntity();
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        exampleEntity.setLanguage("DE");
        matcher = matcher.withMatcher("language", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());

        List<UserEntity> users = userRepository.findByExample(Example.of(exampleEntity, matcher));
        assertEquals(0, users.size());
    }
}
