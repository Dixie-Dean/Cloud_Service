package com.workshop.dixie.service;

import com.workshop.dixie.entity.LoginData;
import com.workshop.dixie.entity.RegisterData;
import com.workshop.dixie.entity.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> register(RegisterData registerData);

    ResponseEntity<TokenDTO> login(LoginData loginData);

}
