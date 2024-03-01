package com.workshop.dixie.security.authentication.controller;

import com.workshop.dixie.security.authentication.model.dto.LoginDTO;
import com.workshop.dixie.security.authentication.model.dto.RegisterDTO;
import com.workshop.dixie.security.authentication.model.dto.TokenDTO;
import com.workshop.dixie.security.authentication.service.implementation.AuthServiceImpl;
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
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return authService.logout();
    }

}
