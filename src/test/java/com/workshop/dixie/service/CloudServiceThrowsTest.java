package com.workshop.dixie.service;

import com.workshop.dixie.application.service.implementation.CloudServiceImpl;
import com.workshop.dixie.application.model.dto.EditFileDTO;
import com.workshop.dixie.application.model.dto.InputFileDTO;
import com.workshop.dixie.security.authentication.model.entity.CloudUser;
import com.workshop.dixie.application.model.entity.File;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.application.repository.CloudFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public class CloudServiceThrowsTest {
    private static CloudServiceImpl cloudService;
    private static FileMapper fileMapper;
    private static File file;

    @BeforeAll
    public static void beforeAll() {
        CloudUser mockedCloudUser = Mockito.mock(CloudUser.class);
        file = new File(String.valueOf(UUID.randomUUID()), "Test File", "Content", mockedCloudUser);

        CloudFileRepository cloudFileRepository = Mockito.mock(CloudFileRepository.class);
        Mockito.when(cloudFileRepository.uploadFile(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(cloudFileRepository.deleteFile(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(cloudFileRepository.downloadFile(Mockito.anyString()))
                .thenReturn(Optional.empty());


        cloudService = new CloudServiceImpl(cloudFileRepository, fileMapper);
    }

    @Test
    public void uploadThrowsErrorInputDataException() {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.isEmpty()).thenReturn(true);

        InputFileDTO inputFileDTO = new InputFileDTO();
        inputFileDTO.setHash("Hash");
        inputFileDTO.setFile(multipartFile);

        Executable executable = () -> cloudService.uploadFile(file.getFilename(), inputFileDTO);
        Assertions.assertThrows(ErrorInputDataException.class, executable);
    }

    @Test
    public void deleteThrowsInternalServerException() {
        Executable executable = () -> cloudService.deleteFile(file.getFilename());
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void downloadThrowsInternalServerException() {
        Executable executable = () -> cloudService.downloadFile(file.getFilename());
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void editThrowsInternalServerException() {
        EditFileDTO editFileDTO = new EditFileDTO();
        editFileDTO.setName("RENAMED");

        Executable executable = () -> cloudService.editFileName(file.getFilename(), editFileDTO);
        Assertions.assertThrows(InternalServerException.class, executable);
    }

    @Test
    public void getAllThrowsErrorInputDataException() {
        Executable executable = () -> cloudService.getAllFiles(-1);
        Assertions.assertThrows(ErrorInputDataException.class, executable);
    }
}
