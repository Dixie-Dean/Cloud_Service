package com.workshop.dixie.service;

import com.workshop.dixie.entity.EditFileDTO;
import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.exception.UnauthorizedException;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.repository.CloudFileRepository;
import com.workshop.dixie.security.TokenManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public class CloudServiceThrowsTest {
    private static final String TEST_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9";
    private static final String USERNAME = "User";
    private static TokenManager tokenManager;
    private static CloudServiceImpl cloudService;
    private static final File file = new File(
            String.valueOf(UUID.randomUUID()), "Test File", "Content", USERNAME);

    @BeforeAll
    public static void beforeAll() {
        tokenManager = Mockito.mock(TokenManager.class);

        CloudFileRepository cloudFileRepository = Mockito.mock(CloudFileRepository.class);
        Mockito.when(cloudFileRepository.uploadFile(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(cloudFileRepository.deleteFile(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(cloudFileRepository.downloadFile(Mockito.anyString()))
                .thenReturn(Optional.empty());

        FileMapper fileMapper = new FileMapper();
        cloudService = new CloudServiceImpl(cloudFileRepository, fileMapper, tokenManager);
    }

    @AfterEach
    public void afterEach() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(false);
    }

    @Test
    public void uploadThrowsUnauthorizedException() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(true);

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        Executable executable = () -> cloudService.uploadFile(TEST_TOKEN, file.getFilename(), inputFileDTO);
        Assertions.assertThrows(UnauthorizedException.class, executable);
    }

    @Test
    public void uploadThrowsErrorInputDataException() {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.isEmpty()).thenReturn(true);

        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        Executable executable = () -> cloudService.uploadFile(TEST_TOKEN, file.getFilename(), inputFileDTO);
        Assertions.assertThrows(ErrorInputDataException.class, executable);
    }

    @Test
    public void uploadThrowsInternalServerException() {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        Executable executable = () -> cloudService.uploadFile(TEST_TOKEN, file.getFilename(), inputFileDTO);
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void deleteThrowsUnauthorizedException() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(true);

        Executable executable = () -> cloudService.deleteFile(TEST_TOKEN, file.getFilename());
        Assertions.assertThrows(UnauthorizedException.class, executable);
    }

    @Test
    public void deleteThrowsInternalServerException() {
        Executable executable = () -> cloudService.deleteFile(TEST_TOKEN, file.getFilename());
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void downloadThrowsUnauthorizedException() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(true);

        Executable executable = () -> cloudService.downloadFile(TEST_TOKEN, file.getFilename());
        Assertions.assertThrows(UnauthorizedException.class, executable);
    }

    @Test
    public void downloadThrowsInternalServerException() {
        Executable executable = () -> cloudService.downloadFile(TEST_TOKEN, file.getFilename());
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void editThrowsUnauthorizedException() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(true);

        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        Executable executable = () -> cloudService.editFileName(TEST_TOKEN, file.getFilename(), editFileDTO);
        Assertions.assertThrows(UnauthorizedException.class, executable);
    }

    @Test
    public void editThrowsInternalServerException() {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        Executable executable = () -> cloudService.editFileName(TEST_TOKEN, file.getFilename(), editFileDTO);
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void getAllThrowsUnauthorizedException() {
        Mockito.when(tokenManager.validateToken(TEST_TOKEN)).thenReturn(true);

        Executable executable = () -> cloudService.getAllFiles(TEST_TOKEN, 1);
        Assertions.assertThrows(UnauthorizedException.class, executable);
    }

    @Test
    public void getAllThrowsErrorInputDataException() {
        Executable executable = () -> cloudService.getAllFiles(TEST_TOKEN, -1);
        Assertions.assertThrows(ErrorInputDataException.class, executable);
    }
}
