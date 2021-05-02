package com.epam.turik.restchat.model;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.user.UserEntity;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDTO userDTO) {
        //
    }

    public User findById(long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return null;
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return new ArrayList<>();
    }

    public void updateUser(UserDTO data) {
        if (!userRepository.existsById(data.getId())) {
            throw new UserNotFoundException();
        }
        UserEntity user = userRepository.findById(data.getId()).orElse(null);
        if (user == null) {
            return;
        }
        user.setUsername(data.getUsername());
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
