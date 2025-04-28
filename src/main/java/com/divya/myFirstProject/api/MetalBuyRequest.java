package com.divya.myFirstProject.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetalBuyRequest {
    private int fromUserId;    //buy
    private int toUserId;  //sell
    private String currency;
    private String metalType;
    private double metalQuantity;
}
