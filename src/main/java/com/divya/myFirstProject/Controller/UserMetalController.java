package com.divya.myFirstProject.Controller;

import com.divya.myFirstProject.entity.UserMetal;
import com.divya.myFirstProject.service.UserMetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Metal")
public class UserMetalController {

    @Autowired
    private UserMetalService userMetalService;

   @PostMapping("/MetalDeposit")
   public UserMetal depositMetal(@RequestParam(name="id") Integer UserId,
                                 @RequestParam(name="type") String metal_type,
                                 @RequestParam(name="quantity") double metal_quantity){
    return userMetalService.depositMetal(UserId,metal_type, metal_quantity);
    }

    @GetMapping("/{userId}/{MetalType}")
    public UserMetal getMetal(@PathVariable int userId, @PathVariable String MetalType){
       return userMetalService.getUserMetals(userId, MetalType);
    }
}
