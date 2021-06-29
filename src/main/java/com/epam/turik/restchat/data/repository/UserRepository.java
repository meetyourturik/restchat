package com.epam.turik.restchat.data.repository;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, CustomUserRepository {

    List<UserEntity> findAll(Example<UserEntity> userEntityExample);

}
