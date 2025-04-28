package com.divya.myFirstProject.service;

import com.divya.myFirstProject.Repository.TransactionRepository;
import com.divya.myFirstProject.Repository.WalletRepository;
import com.divya.myFirstProject.api.SendMoney;
import com.divya.myFirstProject.entity.Transaction;
import com.divya.myFirstProject.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;



    public Wallet createWallet(int id, String currency, double balance) {
        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setCurrency(currency);
        wallet.setBalance(balance);
        return walletRepository.save(wallet);
    }
    public Wallet getBalance(Integer userId, String currency) {
        Wallet wallets = walletRepository.findByIdAndCurrency(userId, currency);
//     if (wallets.isEmpty()) {
//         throw new RuntimeException("No wallets found for currency: " + currency);
//     }
        return wallets;
    }

    public double addBalanceBYCurrency(String currency) {
        List<Wallet> wallets = this.getWalletsByCurrency(currency);
        double sum = 0;
        for (int i = 0; i < wallets.size(); i++) {
            sum = sum + wallets.get(i).getBalance();
        }
        return sum;
    }
    public List<Wallet> getWalletsByCurrency(String currency) {

        return walletRepository.findAllByCurrency(currency);
    }
    public List<Wallet> getUserWallets(Integer userId) {

        return walletRepository.findAllById(userId);
    }

    @Transactional
    public Wallet deposit(Integer userId, String currency, double amount) {
        List<Wallet> wallets = walletRepository.findAllById(userId);//.orElseThrow(() ->new RuntimeException("Wallet not found"));
        if (amount <= 0) {
            throw new RuntimeException("Deposit amount should be positive");
        }
        for (int i = 0; i < wallets.size(); i++) {
            if (wallets.get(i).getCurrency().equals(currency)) {
                wallets.get(i).setBalance(wallets.get(i).getBalance() + amount);
                walletRepository.save(wallets.get(i));
//             if(amount>200){
//                 throw new RuntimeException("exceed balance in this");
//             }
                Transaction transaction = new Transaction();
                transaction.setTransactionType("CREDITED");
                transaction.setWalletId(wallets.get(i).getWalletId());
                transaction.setAmount(amount);
                transaction.setTime(LocalDateTime.now());
                transactionRepository.save(transaction);
                return wallets.get(i);

            }
        }
        throw new RuntimeException("user wallet currency not found");

    }
  @Transactional
    public Wallet withdraw(Integer userId, String currency, double amount) {
        List<Wallet> wallets = walletRepository.findAllById(userId);//.orElseThrow(() ->new RuntimeException("Wallet not found"));
        if (amount <= 0) {
            throw new RuntimeException("Withdraw amount should be positive");
        }
        for (int i = 0; i < wallets.size(); i++) {
            if (wallets.get(i).getCurrency().equals(currency)) {
                if (wallets.get(i).getBalance() >= amount) {
                    wallets.get(i).setBalance(wallets.get(i).getBalance() - amount);
                    walletRepository.save(wallets.get(i));

                    Transaction transaction = new Transaction();

                    transaction.setTransactionType("WITHDRAW");
                    transaction.setWalletId(wallets.get(i).getWalletId());
                    transaction.setAmount(amount);
                    transaction.setTime(LocalDateTime.now());

                    transactionRepository.save(transaction);
                    return wallets.get(i);
                }
            }
        }
        throw new RuntimeException("invalid currency");
    }

    @Transactional
    public String moneyTransfer(SendMoney sendMoney, Integer userId){
        int fromWalletId= sendMoney.getFromWalletId();
        int toWalletId= sendMoney.getToWalletId();
        double amount= sendMoney.getBalance();

        Wallet walletIdFrom= walletRepository.findByWalletId(fromWalletId);
        Wallet walletIdTo= walletRepository.findByWalletId(toWalletId);
        if (walletIdFrom.getId()!=(userId)) {
            throw new RuntimeException("Wallet does not belong to the given user ID");
        }
        if(walletIdFrom.getCurrency().equals(walletIdTo.getCurrency())){
            walletIdFrom.setBalance(walletIdFrom.getBalance()-amount);
            walletRepository.save(walletIdFrom);

            Transaction transaction = new Transaction();
            transaction.setTransactionType("WITHDRAW");
            transaction.setWalletId(walletIdFrom.getWalletId());
            transaction.setAmount(amount);
            transaction.setTime(LocalDateTime.now());
            transactionRepository.save(transaction);

            walletIdTo.setBalance(walletIdTo.getBalance()+amount);
            walletRepository.save(walletIdTo);

            Transaction transaction1 = new Transaction();
            transaction1.setTransactionType("CREDITED");
            transaction1.setWalletId(walletIdTo.getWalletId());
            transaction1.setAmount(amount);
            transaction1.setTime(LocalDateTime.now());
            transactionRepository.save(transaction1);
            return "transfer success";
        }
        throw new RuntimeException("different currency");
    }

    public List<Transaction> getTransactionhistory(Integer walletId){
        return transactionRepository.findByWalletId(walletId);
    }

}