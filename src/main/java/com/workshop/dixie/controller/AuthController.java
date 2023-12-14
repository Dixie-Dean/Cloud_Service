package com.workshop.dixie.controller;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.LoginData;
import com.workshop.dixie.entity.RegisterData;
import com.workshop.dixie.repository.CloudUserRepository;
import com.workshop.dixie.security.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloud")
public class AuthController {
    private final CloudUserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    public AuthController(CloudUserRepository repository,
                          AuthenticationManager authenticationManager,
                          TokenProvider tokenProvider,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.encoder = encoder;
    }

    @PostMapping("/register")
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginData loginData) {
        String jsonTokenTemplate = "{" + "\"auth-token\": \"%s\"" + "}";

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginData.getLogin(),
                loginData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(String.format(jsonTokenTemplate, token), HttpStatus.OK);
    }

}
