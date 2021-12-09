package com.epam.turik.restchat.data.repository;

import com.epam.turik.restchat.data.objects.user.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    public CustomUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<UserEntity> findUserById(Long id) {
        TypedQuery<UserEntity> query = entityManager.createQuery("select u from user u where id = :id", UserEntity.class);
        query.setParameter("id", id);
        List<UserEntity> userEntities = query.getResultList();
        return userEntities.isEmpty() ? Optional.empty() : Optional.ofNullable(userEntities.get(0));
    }
}
