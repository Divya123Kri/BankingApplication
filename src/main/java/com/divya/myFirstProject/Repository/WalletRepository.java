package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllById(Integer Id);
    Wallet findByIdAndCurrency(Integer userId, String currency);
    List<Wallet> findAllByCurrency(String currency);
    Wallet findByWalletId(Integer walletId);



}

