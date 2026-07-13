package com.example.fraudsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenValidator {
    private final SecretKey key;

    public JwtTokenValidator(@Value("${service-jwt-secret}")String secret){
        this.key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Claims validationToken(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
