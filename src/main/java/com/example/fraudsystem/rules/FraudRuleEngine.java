package com.example.fraudsystem.rules;

import com.example.fraudsystem.dto.FraudRequest;
import com.example.fraudsystem.dto.FraudResponse;
import com.example.fraudsystem.enums.FraudStatus;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class FraudRuleEngine {

    private static final List<String> BLOCKED_CARDS = List.of(
            "9999888877776666",
            "1111222233334444"
    );

    public FraudResponse checkFraud(FraudRequest req) {

        FraudResponse res = new FraudResponse();

        if (req.getCardNo() == null || req.getCardNo().length() != 16) {
            res.setStatus(FraudStatus.FRAUD);
            res.setMessage("Invalid card number");
            return res;
        }

        if (!req.getCardNo().matches("\\d{16}")) {
            res.setStatus(FraudStatus.FRAUD);
            res.setMessage("Card number must contain only digits");
            return res;
        }

        if (BLOCKED_CARDS.contains(req.getCardNo())) {
            res.setStatus(FraudStatus.FRAUD);
            res.setMessage("Blocked card");
            return res;
        }

        if (req.getAmount() <= 0) {
            res.setStatus(FraudStatus.FRAUD);
            res.setMessage("Invalid payment amount");
            return res;
        }

        if (req.getAmount() > 50000) {
            res.setStatus(FraudStatus.FRAUD);
            res.setMessage("Amount exceeds limit");
            return res;
        }

        res.setStatus(FraudStatus.SAFE);
        res.setMessage("Payment verified successfully");

        return res;
    }
}