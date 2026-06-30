package com.example.fraudsystem.dto;

import com.example.fraudsystem.enums.FraudStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraudResponse {
    private FraudStatus status;
    private String message;
}
