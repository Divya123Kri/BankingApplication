package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.entity.Wallet;
import com.divya.myFirstProject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

   @Autowired
   private WalletService walletService;

  @GetMapping("/user/{userId}")
  public List<Wallet> getUserWallets(@PathVariable Integer userId){

      return walletService.getUserWallets(userId);
   }

   @PostMapping("/deposit")
   public Wallet deposit(@RequestParam(name="id") Integer userId,
                         @RequestParam(name="cur") String currency,
                         @RequestParam(name="am") double amount){
      return walletService.deposit(userId,currency,amount);
   }
    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestParam(name="id") Integer userId,
                           @RequestParam(name="cur") String currency,
                           @RequestParam(name="am") double amount){
        return walletService.withdraw(userId,currency,amount);
    }



}
