package com.workshop.dixie.service;

import com.workshop.dixie.entity.LoginDTO;
import com.workshop.dixie.mapper.TokenMapper;
import com.workshop.dixie.repository.CloudUserRepository;
import com.workshop.dixie.repository.TokenRepository;
import com.workshop.dixie.security.TokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceThrowsTest {
    private static final String TEST_LOGIN = "UserLogin";
    private static final String TEST_PASSWORD = "UserPassword";
    private static AuthService authService;

    @BeforeAll
    public static void beforeAll() {
        CloudUserRepository cloudUserRepository = Mockito.mock(CloudUserRepository.class);
        Mockito.when(cloudUserRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        TokenManager tokenManager = Mockito.mock(TokenManager.class);
        TokenMapper tokenMapper = new TokenMapper();
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

        authService = new AuthServiceImpl(
                cloudUserRepository, tokenRepository,
                encoder, tokenManager, tokenMapper,
                authenticationManager);
    }

    @Test
    public void loginThrowsBadCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(TEST_LOGIN);
        loginDTO.setPassword(TEST_PASSWORD);

        Executable executable = () -> authService.login(loginDTO);
        Assertions.assertThrows(BadCredentialsException.class, executable);
    }

}
