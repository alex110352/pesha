package com.example.pesha.controller;

import com.example.pesha.dao.entity.User;
import com.example.pesha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/admin/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping
    public User createUser(@RequestBody User requestUser) {
        return userService.createUser(requestUser);
    }

    @PutMapping
    public User replaceUser(@RequestParam("userId") Long userId,
                            @RequestBody User requestUser) {
        return userService.replaceUser(userId, requestUser);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
    }

}
