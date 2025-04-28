package com.divya.myFirstProject.service;

import com.divya.myFirstProject.Repository.UserRepository;
import com.divya.myFirstProject.api.UserResponse;
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

    public UserResponse registerUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("username already exists");
        }
        User user1= userRepository.save(user);
        walletService.createWallet(user1.getId(),"usd",0);
        walletService.createWallet(user1.getId(),"inr",0);
        return new UserResponse(user1.getUsername(),user1.getEmail(),user1.getWallets());
    }
    public UserResponse getUserById(Integer id) {
        UserResponse user1= new UserResponse();

        User user =userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return new UserResponse(user.getUsername(), user.getEmail(), user.getWallets());

   }
}
