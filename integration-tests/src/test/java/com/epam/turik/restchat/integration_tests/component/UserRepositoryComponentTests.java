package com.epam.turik.restchat.integration_tests.component;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.integration_tests.infrastructure.ComponentTest;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.vladmihalcea.hibernate.type.basic.Inet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public DatabaseConfigBean getConfigBean() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setEscapePattern("\"");
        return bean;
    }

    @Bean(name = "connectionBean")
    public DatabaseDataSourceConnectionFactoryBean getConnectionBean(DatabaseConfigBean config) {
        DatabaseDataSourceConnectionFactoryBean connectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean();
        connectionFactoryBean.setDatabaseConfig(config);
        return connectionFactoryBean;
    }

    @Test
    @DisplayName("should return user by id")
    @DatabaseSetup(connection = "connectionBean", value = "/dbunit/two-full-users.xml")
    void getUserByIdTest() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setStatus(UserStatus.ACTIVE);
        user1.setEmail("user1@email.com");
        user1.setTimezone("GMT");
        user1.setLanguage("RU");
        user1.setDeletionDate(Timestamp.valueOf("2012-12-21 13:17:49.012"));
        user1.setChatPermission(ChatPermission.FRIENDS_ONLY);
        user1.setIp(new Inet("192.168.0.1"));

        // since id
        Optional<UserEntity> user = userRepository.findUserById(1L);
        log.warn("{}", userRepository.findAll().size());
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

        List<UserEntity> users = userRepository.findAll(Example.of(exampleEntity, matcher));
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("should find zero users by bad example")
    void findZeroByExampleTest() {
        UserEntity exampleEntity = new UserEntity();
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        exampleEntity.setLanguage("DE");
        matcher = matcher.withMatcher("language", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());

        List<UserEntity> users = userRepository.findAll(Example.of(exampleEntity, matcher));
        assertEquals(0, users.size());
    }
}
