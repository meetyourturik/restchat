package com.epam.turik.restchat.data;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.types.user.ChatPermission;
import com.epam.turik.restchat.types.user.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);

    List<UserEntity> findByUsernameStartsWithAndStatusAndLanguageAndChatPermission(String username, UserStatus status, String language, ChatPermission chatPermission);
}
