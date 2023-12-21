package com.workshop.dixie.service;

import com.workshop.dixie.entity.LoginDTO;
import com.workshop.dixie.entity.RegisterDTO;
import com.workshop.dixie.entity.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> register(RegisterDTO registerDTO);

    ResponseEntity<TokenDTO> login(LoginDTO loginDTO);

}
