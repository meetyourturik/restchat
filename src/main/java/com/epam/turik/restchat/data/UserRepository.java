package com.epam.turik.restchat.data;

import com.epam.turik.restchat.data.objects.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(
            value = "select u from user u where (:username = '' or u.username like :username%) and " +
                    "(:language = '' or u.language = :language) and " +
                    "(:status = '' or cast(u.status as text) = :status) and " +
                    "(:chatPermission = '' or cast(u.chatPermission as text) = :chatPermission)"
    )
    List<UserEntity> findByUserFilter(String username, String language, String status, String chatPermission);
}
