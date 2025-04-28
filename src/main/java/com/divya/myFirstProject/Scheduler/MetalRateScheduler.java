package com.divya.myFirstProject.Scheduler;

import com.divya.myFirstProject.service.MetalRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MetalRateScheduler {

   @Autowired
   private MetalRateService metalRateService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate =300000)//millisecond
    public void MetalRateScheduled(){
       String currentTime = LocalDateTime.now().format(FORMATTER);
       System.out.println("Updating Metal Rates: " +currentTime);
       metalRateService.updateMethodRates();

   }
}
