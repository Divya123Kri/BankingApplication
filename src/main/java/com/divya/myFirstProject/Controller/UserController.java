package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.api.SendMoney;
import com.divya.myFirstProject.api.UserResponse;
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
    @Autowired
    private WalletService walletService;

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {

        return userService.getUserById(id);
    }

    @PostMapping("/send")
    public String transferMoney(@RequestBody SendMoney sendMoney, @RequestHeader("id") Integer userId) {
        return walletService.moneyTransfer(sendMoney, userId);
    }

}
