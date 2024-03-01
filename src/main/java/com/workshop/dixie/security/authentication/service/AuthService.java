package com.workshop.dixie.security.authentication.service;

import com.workshop.dixie.security.authentication.model.dto.LoginDTO;
import com.workshop.dixie.security.authentication.model.dto.RegisterDTO;
import com.workshop.dixie.security.authentication.model.dto.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(RegisterDTO registerDTO);
    ResponseEntity<TokenDTO> login(LoginDTO loginDTO);
    ResponseEntity<String> logout();
}
