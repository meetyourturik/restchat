package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.repository.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.UserUpdate;
import com.epam.turik.restchat.model.objects.user.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserModelMapper userModelMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserModelMapper userModelMapper) {
        this.userRepository = userRepository;
        this.userModelMapper = userModelMapper;
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
        Example<UserEntity> example = userModelMapper.filterToExample(userFilter);
        List<UserEntity> userEntities = userRepository.findByExample(example);
        return userModelMapper.fromEntityList(userEntities);
    }

    public User updateUser(Long id, UserUpdate update) {
        User user = this.getUserById(id);
        userModelMapper.updateUser(update, user);
        UserEntity entity = userModelMapper.toEntity(user);
        userRepository.save(entity);
        return user;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
