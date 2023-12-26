package com.workshop.dixie.service;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.LoginDTO;
import com.workshop.dixie.entity.RegisterDTO;
import com.workshop.dixie.mapper.TokenMapper;
import com.workshop.dixie.repository.CloudUserRepository;
import com.workshop.dixie.repository.TokenRepository;
import com.workshop.dixie.security.Token;
import com.workshop.dixie.security.TokenDTO;
import com.workshop.dixie.security.TokenManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final CloudUserRepository cloudUserRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;
    private final TokenManager tokenManager;
    private final TokenMapper tokenMapper;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(CloudUserRepository cloudUserRepository,
                           TokenRepository tokenRepository,
                           PasswordEncoder encoder,
                           TokenManager tokenManager,
                           TokenMapper tokenMapper,
                           AuthenticationManager authenticationManager) {
        this.cloudUserRepository = cloudUserRepository;
        this.tokenRepository = tokenRepository;
        this.encoder = encoder;
        this.tokenManager = tokenManager;
        this.tokenMapper = tokenMapper;
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
            throw new BadCredentialsException("Incorrect login or password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getLogin(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Token token = new Token(tokenManager.generateToken(authentication), false);
        tokenRepository.save(token);

        return new ResponseEntity<>(tokenMapper.toTokenDTO(token), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> logout(String token) {
        String[] tokenParts = token.split(" ");
        Optional<String> response = tokenRepository.revokeToken(tokenParts[1]);
        return response.map(string -> new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow();
    }
}
