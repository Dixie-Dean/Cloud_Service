package com.workshop.dixie.service;

import com.workshop.dixie.dto.LoginDTO;
import com.workshop.dixie.dto.RegisterDTO;
import com.workshop.dixie.dto.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(RegisterDTO registerDTO);
    ResponseEntity<TokenDTO> login(LoginDTO loginDTO);
    ResponseEntity<String> logout();
}
