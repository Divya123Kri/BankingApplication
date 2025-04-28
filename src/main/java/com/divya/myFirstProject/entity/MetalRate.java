package com.divya.myFirstProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Metal_Rate")
public class MetalRate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int metalRateId;
    private String metalType;
    private int metalRate;
    private LocalDateTime time;

}
