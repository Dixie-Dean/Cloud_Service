package com.workshop.dixie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "cloud_schema", name = "users")
@Entity
public class CloudUser {

    public CloudUser(String username, String lastname, String email, String password, String roles) {
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles")
    private String roles;

}
