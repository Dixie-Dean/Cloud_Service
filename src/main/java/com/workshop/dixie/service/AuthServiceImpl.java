package com.workshop.dixie.service;

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
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final CloudUserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(CloudUserRepository repository,
                           PasswordEncoder encoder,
                           TokenProvider tokenProvider,
                           AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<String> register(RegisterData registerData) {
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

    @Override
    public ResponseEntity<String> login(LoginData loginData) {
        String jsonTokenTemplate = "{" + "\"auth-token\": \"%s\"" + "}";

        if (!repository.existsByEmail(loginData.getLogin())) {
            return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginData.getLogin(),
                loginData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(String.format(jsonTokenTemplate, token), HttpStatus.OK);
    }
}
