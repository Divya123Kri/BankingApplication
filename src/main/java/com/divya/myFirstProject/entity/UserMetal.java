package com.divya.myFirstProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "User_Metal")
public class UserMetal {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int metalId;
    private int userid;
    private String metalType;
    private double metalQuantity; //in gram
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    //metal unit later

}
