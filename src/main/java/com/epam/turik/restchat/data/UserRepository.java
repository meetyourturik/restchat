package com.epam.turik.restchat.data;

import com.epam.turik.restchat.data.objects.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, Long> {
}
