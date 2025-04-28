package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.api.MetalBuyRequest;
import com.divya.myFirstProject.api.SendMoney;
import com.divya.myFirstProject.entity.MetalRate;
import com.divya.myFirstProject.entity.MetalTransactionHistory;
import com.divya.myFirstProject.entity.Transaction;
import com.divya.myFirstProject.service.MetalRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metalRate")
public class MetalRateController {
   @Autowired
   private MetalRateService metalRateService;

   @PostMapping("/add")
   public MetalRate saveMetalRate(@RequestParam String metalType, @RequestParam int metalRate){
       return metalRateService.saveMetalRate(metalType, metalRate);
   }

  @GetMapping("/latest")
  public MetalRate getLatestMetalRate(@RequestParam String metalType){
       return metalRateService.getLatestMetalRate(metalType);
   }

    @PostMapping("/metalTransfer")
    public String transferMetal(@RequestBody MetalBuyRequest metalBuyRequest) {
        return metalRateService.buyMetal(metalBuyRequest);
    }

    @GetMapping("/{userId}/metalTransactions")
    public List<MetalTransactionHistory> getTransactionHistory(@PathVariable Integer userId){
        return metalRateService.getMetalTransactionhistory(userId);
    }
}
