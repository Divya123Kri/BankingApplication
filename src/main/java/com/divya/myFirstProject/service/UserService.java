package com.divya.myFirstProject.service;

import com.divya.myFirstProject.Repository.UserRepository;
import com.divya.myFirstProject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletService walletService;

    public User registerUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("username already exists");
        }
        User user1= userRepository.save(user);
       // for(int i=0;i<user.getWallets().size();i++){
            walletService.createWallet(user1.getId(),"usd",0);
        walletService.createWallet(user1.getId(),"inr",0);
        return user1;
       // }
    }
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}
