package com.workshop.dixie.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Token {

    @Id
    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "username")
    private String user;
}
