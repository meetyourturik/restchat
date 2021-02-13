package com.epam.turik.restchat.rest;

import com.epam.turik.restchat.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "turik");
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping("/id")
    public void deleteUser(@PathVariable Long id) {
        System.out.println("user deleted");
    }

    @PutMapping("/id")
    public void updateUser(@PathVariable Long id){
        System.out.println("user updated");
    }
}
