package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.ReportDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.rest.objects.UserFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return userRestMapper.toDTO(user);
    }

    @GetMapping("")
    public List<UserDTO> getAll(UserFilter userFilter) {
        List<User> users = userService.getUsersByFilter(userFilter);
        return userRestMapper.toDTOList(users);
    }

    @PostMapping("")
    public void createUser(@RequestBody UserDTO userDTO) {
        User user = userRestMapper.fromDTO(userDTO);
        userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.getUserById(id);
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
