package com.example.fraudsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FraudRequest {
    private String cardNo;
    private double amount;
}
