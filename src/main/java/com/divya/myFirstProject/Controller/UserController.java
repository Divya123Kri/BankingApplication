package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.entity.User;
import com.divya.myFirstProject.service.UserService;
import com.divya.myFirstProject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {

        return userService.getUserById(id);
    }

}
