package com.workshop.dixie.security;

import com.workshop.dixie.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenManager {
    private static final long JWT_EXPIRATION = 70000;
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final TokenRepository tokenRepository;
    public TokenManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        String[] tokenParts = token.split(" ");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.getEncoded())
                .build()
                .parseClaimsJws(tokenParts[1])
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String tokenValue) {
        String[] tokenParts = tokenValue.split(" ");
        Optional<Token> tokenEntity = tokenRepository.findToken(tokenParts[1]);
        return tokenEntity.map(Token::isRevoked).orElse(true);
    }
}
