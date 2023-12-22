package com.workshop.dixie.mapper;

import com.workshop.dixie.security.Token;
import com.workshop.dixie.security.TokenDTO;

public class TokenMapper {

    public TokenDTO toTokenDTO(Token source) {
        if (source == null) {
            return null;
        }

        TokenDTO destination = new TokenDTO();
        destination.setAuthToken(source.getAuthToken());
        destination.setRevoked(source.isRevoked());

        return destination;
    }
}
