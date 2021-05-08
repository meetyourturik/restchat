package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        UserEntity userEntity = userModelMapper.convertToEntity(user);
        userRepository.save(userEntity);
    }

    public User getUserById(long id) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        return userModelMapper.convertToBusinessObject(userEntity);
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(userModelMapper.convertToBusinessObject(userEntity));
        }
        return users;
    }

    public void updateUser(User user) throws UserNotFoundException {
        // user here is not really User, maybe should be some separate thing
        UserEntity userEntity = userRepository.findById(user.getId()).orElse(null);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }
        userEntity.setUsername(user.getUsername());
        userRepository.save(userEntity);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
