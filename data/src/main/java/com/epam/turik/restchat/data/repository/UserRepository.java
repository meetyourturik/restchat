package com.epam.turik.restchat.data.repository;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {

}
