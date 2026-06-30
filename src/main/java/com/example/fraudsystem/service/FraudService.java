package com.example.fraudsystem.service;
import com.example.fraudsystem.dto.FraudRequest;
import com.example.fraudsystem.dto.FraudResponse;
import com.example.fraudsystem.rules.FraudRuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public  class FraudService {

    @Autowired
    private FraudRuleEngine rul;

    public FraudResponse verify(FraudRequest req) {
        return rul.checkFraud(req);
    }
}

