package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userModelMapper.fromEntity(userEntity);
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return userModelMapper.fromEntityList(userEntities);
    }

    public List<User> getUsersByFilter(UserFilter userFilter) {
        log.warn(userFilter.toString());
        String username = userFilter.getUsername() != null ? userFilter.getUsername() : "";
        String language = userFilter.getLanguage() != null ? userFilter.getLanguage() : "";
        String status = userFilter.getStatus() != null ? userFilter.getStatus().name() : "";
        String chatPermission = userFilter.getChatPermission() != null ? userFilter.getChatPermission().name() : "";

        List<UserEntity> userEntities = userRepository.findByUserFilter(username, language, status, chatPermission);
        return userModelMapper.fromEntityList(userEntities);
    }

    public void updateUser(User user, JsonPatch patch) throws UserNotFoundException, JsonPatchException, JsonProcessingException {
        // user here is not really User, maybe should be some separate thing
        User patched = patchService.applyPatch(user, patch);
        UserEntity updatedEntity = userModelMapper.toEntity(patched);
        userRepository.save(updatedEntity);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
