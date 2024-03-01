package com.workshop.dixie.security.authentication.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenDTO {
    @JsonProperty("auth-token")
    private String authToken;
}
