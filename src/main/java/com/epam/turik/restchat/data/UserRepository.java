package com.epam.turik.restchat.data;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);
}
