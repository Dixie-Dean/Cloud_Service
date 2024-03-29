package com.workshop.dixie.service;

import com.workshop.dixie.security.authentication.model.dto.LoginDTO;
import com.workshop.dixie.security.authentication.model.dto.RegisterDTO;
import com.workshop.dixie.security.authentication.model.dto.TokenDTO;
import com.workshop.dixie.security.authentication.model.entity.CloudUser;
import com.workshop.dixie.security.authentication.model.entity.Token;
import com.workshop.dixie.mapper.TokenMapper;
import com.workshop.dixie.security.authentication.repository.CloudUserRepository;
import com.workshop.dixie.security.authentication.repository.TokenRepository;
import com.workshop.dixie.security.authentication.service.AuthService;
import com.workshop.dixie.security.authentication.service.implementation.AuthServiceImpl;
import com.workshop.dixie.security.jwt.JwtManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

public class AuthServiceWorksTest {
    private final static String TEST_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9";
    private static final String TEST_USERNAME = "Username";
    private static final String TEST_LASTNAME = "Lastname";
    private static final String TEST_LOGIN_EMAIL = "UserLoginEmail";
    private static final String TEST_PASSWORD = "UserPassword";
    private static CloudUserRepository cloudUserRepository;
    private static TokenRepository tokenRepository;
    private static AuthService authService;
    private static TokenMapper tokenMapper;

    @BeforeAll
    public static void beforeAll() {
        cloudUserRepository = Mockito.mock(CloudUserRepository.class);
        Mockito.when(cloudUserRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        tokenRepository = Mockito.mock(TokenRepository.class);
        Mockito.when(tokenRepository.removeToken(Mockito.anyString())).thenReturn(Optional.of("Success logout!"));

        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

        JwtManager jwtManager = Mockito.mock(JwtManager.class);
        Mockito.when(jwtManager.generateToken(Mockito.any())).thenReturn(TEST_TOKEN);

        AuthenticationProvider authenticationProvider = Mockito.mock(AuthenticationProvider.class);

        authService = new AuthServiceImpl(
                cloudUserRepository, tokenRepository, encoder,
                jwtManager, authenticationProvider, tokenMapper);
    }

    @AfterEach
    public void afterEach() {
        Mockito.when(cloudUserRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
    }

    @Test
    public void registerOK() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(TEST_USERNAME);
        registerDTO.setLastname(TEST_LASTNAME);
        registerDTO.setEmail(TEST_LOGIN_EMAIL);
        registerDTO.setPassword(TEST_PASSWORD);

        ResponseEntity<String> expected = new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        ResponseEntity<String> actual = authService.register(registerDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void registerEmailTaken() {
        Mockito.when(cloudUserRepository.existsByEmail(TEST_LOGIN_EMAIL)).thenReturn(true);

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(TEST_USERNAME);
        registerDTO.setLastname(TEST_LASTNAME);
        registerDTO.setEmail(TEST_LOGIN_EMAIL);
        registerDTO.setPassword(TEST_PASSWORD);

        ResponseEntity<String> expected = new ResponseEntity<>("This email is taken!", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> actual = authService.register(registerDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void loginOK() {
        Mockito.when(cloudUserRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        CloudUser cloudUser = Mockito.mock(CloudUser.class);
        Mockito.when(cloudUserRepository.findCloudUserByEmail(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(cloudUser));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(TEST_LOGIN_EMAIL);
        loginDTO.setPassword(TEST_PASSWORD);
        Token token = new Token(TEST_TOKEN, TEST_USERNAME);

        ResponseEntity<TokenDTO> expectedTokenDTOResponseEntity = new ResponseEntity<>(
                tokenMapper.turnIntoDTO(token), HttpStatus.OK);
        String expected = Objects.requireNonNull(expectedTokenDTOResponseEntity.getBody()).getAuthToken();

        ResponseEntity<TokenDTO> actualTokenDTOResponseEntity = authService.login(loginDTO);
        String actual = Objects.requireNonNull(actualTokenDTOResponseEntity.getBody()).getAuthToken();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void loginCallsTokenRepositoryMethod() {
        Mockito.when(cloudUserRepository.existsByEmail(TEST_LOGIN_EMAIL)).thenReturn(true);

        CloudUser cloudUser = Mockito.mock(CloudUser.class);
        Mockito.when(cloudUserRepository.findCloudUserByEmail(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(cloudUser));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(TEST_LOGIN_EMAIL);
        loginDTO.setPassword(TEST_PASSWORD);
        authService.login(loginDTO);

        Mockito.verify(tokenRepository, Mockito.atLeastOnce()).save(Mockito.any());
    }
}
