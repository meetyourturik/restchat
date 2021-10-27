package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.UserService;
import com.epam.turik.restchat.model.exceptions.UserNotFoundException;
import com.epam.turik.restchat.model.objects.user.User;
import com.epam.turik.restchat.rest.exceptions.EntityNotFoundException;
import com.epam.turik.restchat.rest.objects.ReportDTO;
import com.epam.turik.restchat.rest.objects.UserDTO;
import com.epam.turik.restchat.rest.objects.UserFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Transactional
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user;
        try {
            user = userService.getUserById(id);
        } catch(UserNotFoundException ex) {
            throw new EntityNotFoundException("id", id);
        }
        return userRestMapper.toDTO(user);
    }

    @GetMapping("")
    public List<UserDTO> getAll(UserFilter userFilter) {
        List<User> users;
        log.warn(userFilter.toString());
        if (userFilter.isEmpty()) {
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersByFilter(userFilter);
        }
        return userRestMapper.toDTOList(users);
    }

    @PostMapping("")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = userRestMapper.fromDTO(userDTO);
        User returnUser = userService.createUser(user);
        return userRestMapper.toDTO(returnUser);
    }

    @Transactional
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        User user = userService.getUserById(id);
        User updatedUser = userService.updateUser(user, patch);
        return userRestMapper.toDTO(updatedUser);
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
