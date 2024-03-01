package com.workshop.dixie.service;

import com.workshop.dixie.application.service.implementation.CloudServiceImpl;
import com.workshop.dixie.application.model.dto.EditFileDTO;
import com.workshop.dixie.application.model.dto.ResponseFileDTO;
import com.workshop.dixie.security.authentication.model.entity.CloudUser;
import com.workshop.dixie.application.model.entity.File;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.application.repository.CloudFileRepository;
import com.workshop.dixie.security.jwt.JwtManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CloudServiceWorksTest {
    private final static String USERNAME = "User";
    private static CloudFileRepository cloudFileRepository;
    private static FileMapper fileMapper;
    private static CloudServiceImpl cloudService;
    private static File file;

    @BeforeAll
    public static void beforeAll() {
        CloudUser mockedCloudUser = Mockito.mock(CloudUser.class);
        file = new File(String.valueOf(UUID.randomUUID()), "Test File", "Content", mockedCloudUser);

        JwtManager jwtManager = Mockito.mock(JwtManager.class);
        Mockito.when(jwtManager.extractEmailFromJwt(Mockito.anyString())).thenReturn(USERNAME);

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

        cloudService = new CloudServiceImpl(cloudFileRepository, fileMapper);
    }


    @Test
    public void deleteOK() throws InternalServerException {
        ResponseEntity<String> expected = new ResponseEntity<>("File deleted!", HttpStatus.OK);
        ResponseEntity<String> actual = cloudService.deleteFile(file.getFilename());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteCallsRepositoryMethod() throws InternalServerException {
        cloudService.deleteFile(file.getFilename());
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).deleteFile(file.getFilename());
    }

    @Test
    public void downloadOK() throws InternalServerException {
        ResponseEntity<ResponseFileDTO> expected = new ResponseEntity<>(fileMapper.turnIntoDTO(file), HttpStatus.OK);
        ResponseEntity<ResponseFileDTO> actual = cloudService.downloadFile(file.getFilename());

        Assertions.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void downloadCallsRepositoryMethod() throws InternalServerException {
        cloudService.downloadFile(file.getFilename());
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).downloadFile(file.getFilename());
    }

    @Test
    public void editOK() throws InternalServerException {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        ResponseEntity<String> expected = new ResponseEntity<>("Filename edited!", HttpStatus.OK);
        ResponseEntity<String> actual = cloudService.editFileName(file.getFile(), editFileDTO);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void editFileTitleCallRepositoryMethod() throws InternalServerException {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        cloudService.editFileName(file.getFilename(), editFileDTO);
        Mockito.verify(cloudFileRepository, Mockito.atLeastOnce()).editFileName(file.getFilename(), editFileDTO.getName());
    }
}
