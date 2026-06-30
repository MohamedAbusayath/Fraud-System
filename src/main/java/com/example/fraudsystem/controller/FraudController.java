package com.example.fraudsystem.controller;

import com.example.fraudsystem.dto.FraudRequest;
import com.example.fraudsystem.dto.FraudResponse;
import com.example.fraudsystem.service.FraudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fraud")
public class FraudController {

    FraudService ser;

    FraudController(FraudService ser){
        this.ser=ser;
    }

    @PostMapping("/check")
    public ResponseEntity<FraudResponse> post(@RequestBody FraudRequest value) {
        return ResponseEntity.ok(ser.verify(value));
    }
}
