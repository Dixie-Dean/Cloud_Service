package com.workshop.dixie.service;

import com.workshop.dixie.entity.EditFileDTO;
import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.exception.UnauthorizedException;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.repository.CloudFileRepository;
import com.workshop.dixie.repository.TokenRepository;
import com.workshop.dixie.security.Token;
import com.workshop.dixie.security.TokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CloudServiceWorksTest {
    private final static String TEST_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9";
    private final static String USERNAME = "User";
    private static CloudFileRepository cloudFileRepository;
    private static final FileMapper fileMapper = new FileMapper();
    private static CloudServiceImpl cloudService;
    private static final File file = new File(
            String.valueOf(UUID.randomUUID()), "Test File", "Content", USERNAME);

    @BeforeAll
    public static void beforeAll() {
        Token token = new Token(TEST_TOKEN, false);

        TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        Mockito.when(tokenRepository.findToken(Mockito.anyString())).thenReturn(Optional.of(token));

        TokenManager tokenManager = Mockito.mock(TokenManager.class);
        Mockito.when(tokenManager.extractEmailFromJwt(Mockito.anyString())).thenReturn(USERNAME);

        cloudFileRepository = Mockito.mock(CloudFileRepository.class);
        Mockito.when(cloudFileRepository.uploadFile(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of("File uploaded!"));

        Mockito.when(cloudFileRepository.deleteFile(Mockito.anyString()))
                .thenReturn(Optional.of("File deleted!"));

        Mockito.when(cloudFileRepository.downloadFile(Mockito.anyString()))
                .thenReturn(Optional.of(file));

        Mockito.when(cloudFileRepository.editFileName(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of("Filename edited!"));

        Mockito.when(cloudFileRepository.getAllFiles(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(List.of(file));

        cloudService = new CloudServiceImpl(cloudFileRepository, fileMapper, tokenManager);
    }

    @Test
    public void uploadOK() throws ErrorInputDataException, InternalServerException, UnauthorizedException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        ResponseEntity<String> expected = new ResponseEntity<>("File uploaded!", HttpStatus.OK);
        ResponseEntity<String> actual = cloudService.uploadFile(TEST_TOKEN, file.getFilename(), inputFileDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void uploadCallsRepositoryMethod() throws ErrorInputDataException, InternalServerException, UnauthorizedException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        cloudService.uploadFile(TEST_TOKEN, file.getFilename(), inputFileDTO);
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce())
                .uploadFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }


    @Test
    public void deleteOK() throws InternalServerException, UnauthorizedException {
        ResponseEntity<String> expected = new ResponseEntity<>("File deleted!", HttpStatus.OK);
        ResponseEntity<String> actual = cloudService.deleteFile(TEST_TOKEN, file.getFilename());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteCallsRepositoryMethod() throws InternalServerException, UnauthorizedException {
        cloudService.deleteFile(TEST_TOKEN, file.getFilename());
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).deleteFile(file.getFilename());
    }

    @Test
    public void downloadOK() throws InternalServerException, UnauthorizedException {
        ResponseEntity<ResponseFileDTO> expected = new ResponseEntity<>(fileMapper.toResponseDTO(file), HttpStatus.OK);
        ResponseEntity<ResponseFileDTO> actual = cloudService.downloadFile(TEST_TOKEN, file.getFilename());

        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void downloadCallsRepositoryMethod() throws InternalServerException, UnauthorizedException {
        cloudService.downloadFile(TEST_TOKEN, file.getFilename());
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).downloadFile(file.getFilename());
    }

    @Test
    public void editOK() throws InternalServerException, UnauthorizedException {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        ResponseEntity<String> expected = new ResponseEntity<>("Filename edited!", HttpStatus.OK);
        ResponseEntity<String> actual = cloudService.editFileName(TEST_TOKEN, file.getFile(), editFileDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void editFileTitleCallRepositoryMethod() throws InternalServerException, UnauthorizedException {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        cloudService.editFileName(TEST_TOKEN, file.getFilename(), editFileDTO);
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).editFileName(file.getFilename(), editFileDTO.getName());
    }

    @Test
    public void getAllFilesOK() throws ErrorInputDataException, UnauthorizedException {
        List<ResponseFileDTO> givenList = List.of(fileMapper.toResponseDTO(file));
        String expected = givenList.get(0).getFilename();
        List<ResponseFileDTO> responseList = cloudService.getAllFiles(TEST_TOKEN, 1);
        String actual = responseList.get(0).getFilename();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllFilesCallRepositoryMethod() throws ErrorInputDataException, UnauthorizedException {
        cloudService.getAllFiles(TEST_TOKEN, 1);
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).getAllFiles(1, USERNAME);
    }
}
