package com.divya.myFirstProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Metal_Transaction_History")
public class MetalTransactionHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int historyId;
    private int userId;
    private String metalType;
    private double metalQuantity;
    private String transactionType;
    private double metalRate;
    private String currency;
    private double totalAmount; // metalrate*quantity
    private LocalDateTime time = LocalDateTime.now();
}
