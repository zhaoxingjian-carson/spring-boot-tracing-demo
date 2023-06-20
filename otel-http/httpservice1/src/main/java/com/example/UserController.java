package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userServiceClient.getUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userServiceClient.createUser(user);
    }
}

