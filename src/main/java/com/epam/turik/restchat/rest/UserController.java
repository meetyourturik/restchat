package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.User;
import com.epam.turik.restchat.data.objects.UserStatus;
import com.epam.turik.restchat.rest.exceptions.UserNotFoundException;
import com.epam.turik.restchat.rest.objects.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/")
    public void addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), UserStatus.valueOf(userDTO.getStatus()));
        userRepository.save(user);
    }

    @PatchMapping("/")
    public void updateUser(@RequestBody UserDTO data){
        if (!userRepository.existsById(data.getId())) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(data.getId()).orElse(null);
        if (user == null) {
            return;
        }
        user.setUsername(data.getUsername());
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
