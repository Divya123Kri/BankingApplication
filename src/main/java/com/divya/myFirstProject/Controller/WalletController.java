package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.api.SendMoney;
import com.divya.myFirstProject.entity.Transaction;
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

    @GetMapping("/balance/{currency}")
    public Wallet getBalance(@RequestHeader("id") Integer userId,
                             @PathVariable String currency){
        return walletService.getBalance(userId, currency);
    }
    @GetMapping("/total/{currency}")
    public double getTotalBalanceByCurrency(@PathVariable String currency){
        return walletService.addBalanceBYCurrency(currency);
    }

    @GetMapping("/{currency}")
    public List<Wallet> getWalletsByCurrency(@PathVariable String currency){
        return walletService.getWalletsByCurrency(currency);
    }

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

    @GetMapping("/{walletId}/transactions")
   public List<Transaction> getTransactionHistory(@PathVariable Integer walletId){
        return walletService.getTransactionhistory(walletId);
   }


}
