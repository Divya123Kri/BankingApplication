package com.divya.myFirstProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets;
    //cascade = CascadeType.ALL- when a User is deleted, all associated wallets are deleted.
    //orphanRemoval = true-Removes any wallet that is no longer associated with a user.

}
