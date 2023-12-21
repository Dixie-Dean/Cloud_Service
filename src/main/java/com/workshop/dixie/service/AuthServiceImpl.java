package com.workshop.dixie.service;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.LoginDTO;
import com.workshop.dixie.entity.RegisterDTO;
import com.workshop.dixie.entity.TokenDTO;
import com.workshop.dixie.repository.CloudUserRepository;
import com.workshop.dixie.security.TokenManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final CloudUserRepository cloudUserRepository;
    private final PasswordEncoder encoder;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(CloudUserRepository cloudUserRepository,
                           PasswordEncoder encoder,
                           TokenManager tokenManager,
                           AuthenticationManager authenticationManager) {
        this.cloudUserRepository = cloudUserRepository;
        this.encoder = encoder;
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        if (cloudUserRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("This email is taken!", HttpStatus.BAD_REQUEST);
        }

        CloudUser cloudUser = new CloudUser(
                registerDTO.getUsername(),
                registerDTO.getLastname(),
                registerDTO.getEmail(),
                encoder.encode(registerDTO.getPassword()),
                "USER"
        );

        cloudUserRepository.save(cloudUser);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        if (!cloudUserRepository.existsByEmail(loginDTO.getLogin())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getLogin(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDTO tokenDTO = new TokenDTO(tokenManager.generateToken(authentication));
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }
}
