package com.divya.myFirstProject.service;

import com.divya.myFirstProject.Repository.UserMetalRepository;
import com.divya.myFirstProject.Repository.UserRepository;
import com.divya.myFirstProject.Repository.WalletRepository;
import com.divya.myFirstProject.entity.User;
import com.divya.myFirstProject.entity.UserMetal;
import com.divya.myFirstProject.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMetalService {

    @Autowired
    private UserMetalRepository userMetalRepository;

   @Autowired
    private UserRepository userRepository;

    public UserMetal showMetal(int userId, String metalType, double metalQuantity){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserMetal userMetal =new UserMetal();
        userMetal.setUserid(userId);
        userMetal.setMetalType(metalType);
        userMetal.setMetalQuantity(metalQuantity);
        return userMetalRepository.save(userMetal);
    }
  @Transactional
    public UserMetal depositMetal(Integer userId, String metalType, double metalQuantity){
        if(metalQuantity<=0){
            throw new RuntimeException("Metal Quantity should be positive");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserMetal> userMetals=userMetalRepository.findAllByuserid(userId);

        for(int i=0;i<userMetals.size();i++){
            if(userMetals.get(i).getMetalType().equals(metalType)){
                userMetals.get(i).setMetalQuantity(userMetals.get(i).getMetalQuantity()+ metalQuantity);
                return userMetalRepository.save(userMetals.get(i));
            }
        }
        UserMetal userMetal =new UserMetal();
        userMetal.setUserid(userId);

        userMetal.setMetalType(metalType);
        userMetal.setMetalQuantity(metalQuantity);
        return userMetalRepository.save(userMetal);

    }

    public UserMetal getUserMetals(int userId, String metalType) {
        return userMetalRepository.findByUseridAndMetalType(userId, metalType);
    }

}
