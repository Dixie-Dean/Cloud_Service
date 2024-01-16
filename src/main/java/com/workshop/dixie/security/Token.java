package com.workshop.dixie.security;

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
    private String authToken;
    private boolean revoked;
}
