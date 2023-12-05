package com.workshop.dixie.controller;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.RegisterData;
import com.workshop.dixie.repository.CloudUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CloudUserRepository repository;
    private final PasswordEncoder encoder;

    public AuthController(CloudUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterData registerData) {
        if (repository.existsByEmail(registerData.getEmail())) {
            return new ResponseEntity<>("This email is taken!", HttpStatus.BAD_REQUEST);
        }

        CloudUser cloudUser = new CloudUser(
                registerData.getUsername(),
                registerData.getLastname(),
                registerData.getEmail(),
                encoder.encode(registerData.getPassword()),
                "USER"
        );

        repository.save(cloudUser);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

}
