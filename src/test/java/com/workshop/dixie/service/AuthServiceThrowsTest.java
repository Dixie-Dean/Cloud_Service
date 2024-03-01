package com.workshop.dixie.service;

import com.workshop.dixie.security.authentication.model.dto.LoginDTO;
import com.workshop.dixie.mapper.TokenMapper;
import com.workshop.dixie.security.authentication.repository.CloudUserRepository;
import com.workshop.dixie.security.authentication.repository.TokenRepository;
import com.workshop.dixie.security.authentication.service.AuthService;
import com.workshop.dixie.security.authentication.service.implementation.AuthServiceImpl;
import com.workshop.dixie.security.jwt.JwtManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceThrowsTest {
    private static final String TEST_LOGIN_EMAIL = "UserLoginEmail";
    private static final String TEST_PASSWORD = "UserPassword";
    private static TokenMapper tokenMapper;
    private static AuthService authService;

    @BeforeAll
    public static void beforeAll() {
        CloudUserRepository cloudUserRepository = Mockito.mock(CloudUserRepository.class);
        Mockito.when(cloudUserRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        JwtManager jwtManager = Mockito.mock(JwtManager.class);
        AuthenticationProvider authenticationProvider = Mockito.mock(AuthenticationProvider.class);

        authService = new AuthServiceImpl(
                cloudUserRepository, tokenRepository, encoder,
                jwtManager, authenticationProvider, tokenMapper);
    }

    @Test
    public void loginThrowsBadCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(TEST_LOGIN_EMAIL);
        loginDTO.setPassword(TEST_PASSWORD);

        Executable executable = () -> authService.login(loginDTO);
        Assertions.assertThrows(BadCredentialsException.class, executable);
    }

}
