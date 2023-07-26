package com.example.pesha.controller;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.service.ProductService;
import com.example.pesha.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SecurityConteoller {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ProductService productService;

    public SecurityConteoller(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
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
    @ResponseBody
    public String fail() {
        return "fail";
    }

    @RequestMapping("/logoutsuccess")
    public String logout() {
        return "logoutsuccess";
    }

    @RequestMapping("/adminpage")
    @ResponseBody
    public String adminpage() {
        return "adminpage";
    }

    @RequestMapping("/managerpage")
    @ResponseBody
    public String managerpage() {
        return "managerpage";
    }

    @RequestMapping("/employeepage")
    @ResponseBody
    public String employeepage() {
        return "employeepage";
    }

}
