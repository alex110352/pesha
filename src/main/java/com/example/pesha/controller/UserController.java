package com.example.pesha.controller;

import com.example.pesha.dao.entity.User;
import com.example.pesha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public User getUser(@RequestParam(value = "userName") String userName) {
        return userService.getUser(userName);
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping
    public User createUser(@RequestBody User requestUser) {
        return userService.createUser(requestUser);
    }

    @PutMapping
    public User replaceUser(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "userPassword") String userPassword,
                            @RequestBody User requestUser) {
        return userService.replaceUser(userName, userPassword, requestUser);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam(value = "userName") String userName) {
        userService.deleteUser(userName);
    }

}
