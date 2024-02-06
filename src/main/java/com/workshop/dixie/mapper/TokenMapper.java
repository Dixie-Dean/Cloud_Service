package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.security.Token;
import com.workshop.dixie.dto.TokenDTO;

public class TokenMapper {

    public TokenDTO toTokenDTO(Token source) {
        if (source == null) {
            return null;
        }

        TokenDTO destination = new TokenDTO();
        destination.setAuthToken(source.getAuthToken());

        return destination;
    }
}
