package com.workshop.dixie.mapper;

import com.workshop.dixie.security.authentication.model.dto.TokenDTO;
import com.workshop.dixie.security.authentication.model.entity.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenDTO turnIntoDTO(Token token);
}
