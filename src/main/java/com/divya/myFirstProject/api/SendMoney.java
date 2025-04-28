package com.divya.myFirstProject.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMoney {

    private int fromWalletId;
    private int toWalletId;
    private double balance;// rename this to amount later
}
