package com.workshop.dixie.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenManager {
    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        
    }
}
