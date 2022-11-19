package com.example.DEM.controller;

import com.example.DEM.entity.User;
import com.example.DEM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-detail-user/{username}")
    public User getUserDetails(@PathVariable("username") String username){
        return userService.getDetailUser(username);
    }
}
