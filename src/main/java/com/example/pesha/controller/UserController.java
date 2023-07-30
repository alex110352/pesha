package com.example.pesha.controller;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.User;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/user/admin/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }


    @PutMapping("/user")
    public User replaceUser(@RequestParam("userId") Long userId,
                            @RequestBody User requestUser) {
        return userService.replaceUser(userId, requestUser);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User userRequest,Model model) {
        try {
            User user = userService.createUser(userRequest);
            model.addAttribute("user",user);
            model.addAttribute("userRequest",userRequest);
            return "redirect:/login";
        } catch (DuplicateException e) {
            model.addAttribute("errorMessage", "user is duplicate");
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }

    }

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/loginpage")
    public String loginpage() {
        return "loginpage";
    }


    @RequestMapping("/fail")
    public String fail() {
        return "fail";
    }

    @RequestMapping("/logoutsuccess")
    public String logout() {
        return "logoutsuccess";
    }

}
