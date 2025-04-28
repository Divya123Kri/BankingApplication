package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.Transaction;
import com.divya.myFirstProject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByWalletId(Integer walletId);
}
