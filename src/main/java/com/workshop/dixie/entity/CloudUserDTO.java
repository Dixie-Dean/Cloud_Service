package com.workshop.dixie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CloudUserDTO {
    private long userId;
    private String username;
    private String lastname;
    private String email;
    private String password;
    private String roles;
}
