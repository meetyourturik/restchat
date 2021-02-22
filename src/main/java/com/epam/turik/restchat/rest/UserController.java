package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.data.UserRepository;
import com.epam.turik.restchat.data.objects.User;
import com.epam.turik.restchat.rest.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
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
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User data){
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(id).orElse(null);
        user.setUsername(data.getUsername());
        userRepository.save(user);
    }
}
