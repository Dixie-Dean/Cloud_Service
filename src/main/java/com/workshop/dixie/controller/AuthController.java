package com.workshop.dixie.controller;

import com.workshop.dixie.entity.LoginData;
import com.workshop.dixie.entity.RegisterData;
import com.workshop.dixie.entity.TokenDTO;
import com.workshop.dixie.service.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud")
public class AuthController {
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterData registerData) {
        return authService.register(registerData);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginData loginData) {
        return authService.login(loginData);
    }

}
