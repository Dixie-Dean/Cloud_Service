package com.workshop.dixie.security.authentication.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String lastname;
    private String email;
    private String password;
}
