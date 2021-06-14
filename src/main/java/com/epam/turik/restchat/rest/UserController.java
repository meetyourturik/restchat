package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.objects.ReportDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
        List<User> users;
        if (userFilter.isEmpty()) {
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersByFilter(userFilter);
        }
        return userRestMapper.toDTOList(users);
    }

    @PostMapping("")
    public void createUser(@RequestBody UserDTO userDTO) {
        User user = userRestMapper.fromDTO(userDTO);
        userService.createUser(user);
    }

    @Transactional
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public void updateUser(@PathVariable Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        User user = userService.getUserById(id);
        userService.updateUser(user, patch);
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
