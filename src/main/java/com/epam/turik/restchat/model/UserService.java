package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    private final PatchService patchService;

    @Autowired
    UserService(UserRepository userRepository, UserModelMapper userModelMapper, PatchService patchService) {
        this.userRepository = userRepository;
        this.userModelMapper = userModelMapper;
        this.patchService = patchService;
    }

    public void createUser(User user) {
        UserEntity userEntity = userModelMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    public User getUserById(long id) throws UserNotFoundException {
        // instead of CrudRepository's 'findById' using custom here just to try it out
        UserEntity userEntity = userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);
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

    public void updateUser(User user, JsonPatch patch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        User patched = patchService.applyPatch(user, patch);
        UserEntity updatedEntity = userModelMapper.toEntity(patched);
        userRepository.save(updatedEntity);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
