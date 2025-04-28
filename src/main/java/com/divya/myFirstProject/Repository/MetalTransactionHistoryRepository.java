package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.MetalTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetalTransactionHistoryRepository extends JpaRepository<MetalTransactionHistory, Integer> {
    List<MetalTransactionHistory> findByUserId(Integer userId);
}
