package com.divya.myFirstProject.service;

import com.divya.myFirstProject.Repository.WalletRepository;
import com.divya.myFirstProject.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> getUserWallets(Integer userId) {
        return walletRepository.findAllById(userId);
    }

    public Wallet createWallet(int id, String currency, double balance){
        Wallet wallet= new Wallet();
        wallet.setId(id);
        wallet.setCurrency(currency);
        wallet.setBalance(balance);

        return walletRepository.save(wallet);

    }

    public Wallet deposit(Integer userId,String currency, double amount){
       List <Wallet> wallets= walletRepository.findAllById(userId);//.orElseThrow(() ->new RuntimeException("Wallet not found"));
        if(amount<=0) {
            throw new RuntimeException("Deposit amount should be positive");
        }
       // Wallet wallet = wallets.getFirst();
        for(int i=0;i< wallets.size();i++){
            if(wallets.get(i).getCurrency().equals(currency)){
                wallets.get(i).setBalance(wallets.get(i).getBalance()+ amount);
                return  walletRepository.save(wallets.get(i));
            }
        }

        throw new RuntimeException("user wallet currency not found");

    }

    public Wallet withdraw(Integer userId,String currency, double amount){
        List <Wallet> wallets= walletRepository.findAllById(userId);//.orElseThrow(() ->new RuntimeException("Wallet not found"));
        if(amount<=0) {
            throw new RuntimeException("Withdraw amount should be positive");
        }
        for(int i=0;i< wallets.size();i++){
            if(wallets.get(i).getCurrency().equals(currency)){
               if(wallets.get(i).getBalance()>=amount){
                   amount=wallets.get(i).getBalance()-amount;
                  wallets.get(i).setBalance(amount);
                  return walletRepository.save(wallets.get(i));
               }
            }
        }
        throw new RuntimeException("invalid currency");
    }
}
