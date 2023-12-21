package com.workshop.dixie.entity;

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
