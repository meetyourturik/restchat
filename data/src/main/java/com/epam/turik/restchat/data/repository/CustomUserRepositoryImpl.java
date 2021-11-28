package com.epam.turik.restchat.data.repository;

import com.epam.turik.restchat.data.user.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    private final EntityManager entityManager;

    public CustomUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<UserEntity> findUserById(Long id) {
        Query query = entityManager.createNativeQuery("select * from \"user\" where id = ?1", UserEntity.class);
        query.setParameter(1, id);
        List<UserEntity> userEntities = (List<UserEntity>) query.getResultList();
        return userEntities.isEmpty() ? Optional.empty() : Optional.ofNullable(userEntities.get(0));
    }
}
