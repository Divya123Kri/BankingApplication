package com.divya.myFirstProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int transactionId;
    private String transactionType;// withdraw amount/credited amount
    private int walletId;
    private Double amount;
    private LocalDateTime time;
//    @PrePersist
//    protected void onCreate() {
//        time = LocalDateTime.now();


}
