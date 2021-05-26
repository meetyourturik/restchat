package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    UserRepository userRepository;
    UserModelMapper userModelMapper;

    @Autowired
    UserService(UserRepository userRepository, UserModelMapper userModelMapper) {
        this.userRepository = userRepository;
        this.userModelMapper = userModelMapper;
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

    public List<User> filterUsers(List<User> users, UserFilter userFilter) {
        String filterUserName = userFilter.getUsername();
        String filterLanguage = userFilter.getLanguage();
        String filterStatus = userFilter.getStatus();
        String filterChatPermission = userFilter.getChatPermission();
        return users.stream().filter( user ->
                filterUserName == null || user.getUsername().startsWith(filterUserName)
            ).filter( user ->
                filterLanguage == null || user.getLanguage().equals(filterLanguage)
            ).filter( user ->
                filterStatus == null || user.getStatus().name().equals(filterStatus)
            ).filter( user ->
                filterChatPermission == null || user.getChatPermission().name().equals(filterChatPermission)
            ).collect(Collectors.toList());
    }

    public void updateUser(User user) throws UserNotFoundException {
        // user here is not really User, maybe should be some separate thing
        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        userEntity.setUsername(user.getUsername());
        userRepository.save(userEntity);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
