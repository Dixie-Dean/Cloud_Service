package com.workshop.dixie.mapper;

import com.workshop.dixie.dto.TokenDTO;
import com.workshop.dixie.entity.security.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenDTO turnIntoDTO(Token token);

}
