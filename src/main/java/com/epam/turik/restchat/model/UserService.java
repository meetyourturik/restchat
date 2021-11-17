package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.rest.objects.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserModelMapper userModelMapper;
    private final UpdateService updateService;

    @Autowired
    public UserService(UserRepository userRepository, UserModelMapper userModelMapper, UpdateService updateService) {
        this.userRepository = userRepository;
        this.userModelMapper = userModelMapper;
        this.updateService = updateService;
    }

    public User createUser(User user) {
        UserEntity userEntity = userModelMapper.toEntity(user);
        UserEntity returnUser = userRepository.save(userEntity);
        return userModelMapper.fromEntity(returnUser);
    }

    public User getUserById(long id) throws UserNotFoundException {
        // instead of CrudRepository's 'findById' using custom here just to try it out
        UserEntity userEntity = userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelMapper.fromEntity(userEntity);
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return userModelMapper.fromEntityList(userEntities);
    }

    // кривошеев против!
    @Deprecated
    public List<User> getUsersByFilter(UserFilter userFilter) {
        UserEntity exampleEntity = new UserEntity();

        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        if (userFilter.getUsername() != null) {
            exampleEntity.setUsername(userFilter.getUsername());
            matcher = matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());
        }

        if (userFilter.getLanguage() != null) {
            exampleEntity.setLanguage(userFilter.getLanguage());
            matcher = matcher.withMatcher("language", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase());
        }

        if (userFilter.getStatus() != null) {
            exampleEntity.setStatus(userFilter.getStatus());
            matcher = matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        if (userFilter.getChatPermission() != null) {
            exampleEntity.setChatPermission(userFilter.getChatPermission());
            matcher = matcher.withMatcher("chatPermission", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        List<UserEntity> userEntities = userRepository.findAll(Example.of(exampleEntity, matcher));
        return userModelMapper.fromEntityList(userEntities);
    }

    public User updateUser(Long id, UserUpdate update) {
        User user = this.getUserById(id);
        updateService.applyUpdate(user, update);
        UserEntity entity = userModelMapper.toEntity(user);
        userRepository.save(entity);
        return user;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
