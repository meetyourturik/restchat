package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.ReportDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final RestMapper restMapper;

    public UserController(UserService userService, RestMapper restMapper) {
        this.userService = userService;
        this.restMapper = restMapper;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new UserNotFoundException();
//        }
        User user = userService.findById(id);
        UserDTO userDTO = restMapper.transformUser(user);
        return userDTO;
    }

    @GetMapping("/")
    public List<UserDTO> getAll() {
        List<User> users = userService.getAllUsers();
        return new ArrayList<>();
    }

    @PostMapping("/")
    public void addUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @PatchMapping("/")
    public void updateUser(@RequestBody UserDTO data){
        userService.updateUser(data);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new UserNotFoundException();
//        }
        userService.deleteUser(id);
    }

    @PostMapping("/report")
    public void reportUser(ReportDTO data) {
        // post user report
    }
}
