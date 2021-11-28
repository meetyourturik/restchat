package com.epam.turik.restchat.data.repository;

import com.epam.turik.restchat.data.user.UserEntity;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<UserEntity> findUserById(Long id);
}
