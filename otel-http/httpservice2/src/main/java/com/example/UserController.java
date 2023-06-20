package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return new User(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return user;
    }
}

