package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findAllById(Integer Id);
}

