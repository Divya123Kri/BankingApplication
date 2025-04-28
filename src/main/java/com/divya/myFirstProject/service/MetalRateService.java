package com.divya.myFirstProject.service;


import com.divya.myFirstProject.Repository.MetalRateRepository;
import com.divya.myFirstProject.Repository.MetalTransactionHistoryRepository;
import com.divya.myFirstProject.Repository.UserMetalRepository;
import com.divya.myFirstProject.Repository.WalletRepository;
import com.divya.myFirstProject.api.MetalBuyRequest;
import com.divya.myFirstProject.api.SendMoney;
import com.divya.myFirstProject.entity.*;
import com.divya.myFirstProject.thirdParty.MetalRateAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class MetalRateService {
    @Autowired
    private MetalRateAPI metalRateAPI;

    @Autowired
    private MetalRateRepository metalRateRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserMetalRepository userMetalRepository;

    @Autowired
    private MetalTransactionHistoryRepository metalTransactionHistoryRepository;

    @Autowired
    private WalletRepository walletRepository;



   @Transactional
    public MetalRate saveMetalRate(String metalType, double metalRate){
       MetalRate metalRateEntry=new MetalRate();
       metalRateEntry.setMetalType(metalType);
       metalRateEntry.setMetalRate((int) metalRate);
       metalRateEntry.setTime(LocalDateTime.now());
       return metalRateRepository.save(metalRateEntry);
   }
    public MetalRate getLatestMetalRate(String metalType){
        Pageable pageable= PageRequest.of(0,1, Sort.by("time").descending());
        List<MetalRate> rates =metalRateRepository.findLatestMetalRate(metalType, pageable);
        return rates.stream().findFirst().orElse(null);
       //return rates.isEmpty() ? null : rates.get(0);
    }
   @Transactional
    public String buyMetal(MetalBuyRequest metalBuyRequest) {
       int buyerId = metalBuyRequest.getFromUserId();
       int sellerId = metalBuyRequest.getToUserId();
       String currency = metalBuyRequest.getCurrency();
       String metalType = metalBuyRequest.getMetalType();
       double metalQuantity = metalBuyRequest.getMetalQuantity();

       MetalRate latestRate = getLatestMetalRate(metalType);

       double totalPrice = metalQuantity * latestRate.getMetalRate();

       Wallet buyerWallet = walletRepository.findByIdAndCurrency(buyerId, currency);
       Wallet sellerWallet = walletRepository.findByIdAndCurrency(sellerId, currency);

       if(buyerWallet==null || sellerWallet==null){
           throw new RuntimeException("Wallet not found for one of the users");
       }
       if (buyerWallet.getBalance()<totalPrice) {
           throw new RuntimeException("Buyer does not have enough balance");
       }

       SendMoney sendMoney = new SendMoney();
       sendMoney.setFromWalletId(buyerWallet.getWalletId());
       sendMoney.setToWalletId(sellerWallet.getWalletId());
       sendMoney.setBalance(totalPrice);

       walletService.moneyTransfer(sendMoney, buyerId);

       UserMetal buyerMetal = userMetalRepository.findByUseridAndMetalType(buyerId, metalType);
       UserMetal sellerMetal = userMetalRepository.findByUseridAndMetalType(sellerId, metalType);

       if(buyerMetal==null){
           buyerMetal  = new UserMetal();
           buyerMetal.setUserid(buyerId);
           buyerMetal.setMetalType(metalType);
           buyerMetal.setMetalQuantity(0);
       }

       if (sellerMetal.getMetalQuantity() >=metalQuantity) {
           sellerMetal.setMetalQuantity(sellerMetal.getMetalQuantity() - metalQuantity);
           buyerMetal.setMetalQuantity(buyerMetal.getMetalQuantity() + metalQuantity);
           userMetalRepository.save(buyerMetal);
           userMetalRepository.save(sellerMetal);
       }

       MetalTransactionHistory buyerTransaction=new MetalTransactionHistory();
       buyerTransaction.setUserId(buyerId);
       buyerTransaction.setMetalType(metalType);
       buyerTransaction.setMetalQuantity(metalQuantity);
       buyerTransaction.setTransactionType("BUY METAL");
       buyerTransaction.setMetalRate(latestRate.getMetalRate());
       buyerTransaction.setCurrency(currency);
       buyerTransaction.setTotalAmount(totalPrice);
       buyerTransaction.setTime(LocalDateTime.now());
       metalTransactionHistoryRepository.save(buyerTransaction);

       MetalTransactionHistory sellerTransaction=new MetalTransactionHistory();
       sellerTransaction.setUserId(sellerId);
       sellerTransaction.setMetalType(metalType);
       sellerTransaction.setMetalQuantity(metalQuantity);
       sellerTransaction.setTransactionType("SOLD METAL");
       sellerTransaction.setMetalRate(latestRate.getMetalRate());
       sellerTransaction.setCurrency(currency);
       sellerTransaction.setTotalAmount(totalPrice);
       sellerTransaction.setTime(LocalDateTime.now());
       metalTransactionHistoryRepository.save(sellerTransaction);

       return "Metal purchase successful";
   }

    public List<MetalTransactionHistory> getMetalTransactionhistory(Integer userId){
        return metalTransactionHistoryRepository.findByUserId(userId);
    }

    public List<String> getAllMetalTypes() {
        return metalRateRepository.findDistinctMetalTypes();
    }
    private final Random random = new Random();

   public void updateMethodRates(){
           String metalType="gold";
           double metalRate= metalRateAPI.fetchMetalSpotPrice("XAU","INR");

           saveMetalRate(metalType,metalRate);
           System.out.println("Updated " + metalType + " rate: " + metalRate + " INR per gram ");

   }
}
