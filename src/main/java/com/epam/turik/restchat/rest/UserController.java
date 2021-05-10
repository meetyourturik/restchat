package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.ReportDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserRestMapper userRestMapper;

    @Autowired
    public UserController(UserService userService, UserRestMapper userRestMapper) {
        this.userService = userService;
        this.userRestMapper = userRestMapper;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userRestMapper.convertToDTO(user);
    }

    @GetMapping("")
    public List<UserDTO> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user: users) {
            userDTOs.add(userRestMapper.convertToDTO(user));
        }
        return userDTOs;
    }

    @PostMapping("")
    public void createUser(@RequestBody UserDTO userDTO) {
        User user = userRestMapper.convertToBusinessObject(userDTO);
        userService.createUser(user);
    }

    @PatchMapping("")
    public void updateUser(@RequestBody UserDTO userDTO){
        User user = userRestMapper.convertToBusinessObject(userDTO);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/report")
    public void reportUser(ReportDTO reportDTO) {
        // post user report
    }
}
