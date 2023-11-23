package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.repository.CloudRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.UUID;

public class CloudServiceTest {

    private final CloudRepository repository = Mockito.mock(CloudRepository.class);
    private final CloudServiceImpl service = new CloudServiceImpl(repository);
    private final UserFile file = new UserFile(UUID.randomUUID(), "Test File");

    @Test
    public void uploadFileOK() {
        Assertions.assertEquals(service.uploadFile(file), "File uploaded successfully!");
    }

    @Test
    public void uploadFileCallRepositoryMethod() {
        service.uploadFile(file);
        Mockito.verify(repository, Mockito.atLeastOnce()).uploadFile(file);
    }

    @Test
    public void deleteFileOK() {
        Assertions.assertEquals(service.deleteFile(file.getFileId()), "File deleted successfully!");
    }

    @Test
    public void deleteFileCallRepositoryMethod() {
        service.deleteFile(file.getFileId());
        Mockito.verify(repository, Mockito.atLeastOnce()).deleteFile(file.getFileId());
    }

    @Test
    public void downloadFileOK() {
        Assertions.assertEquals(service.downloadFile(file.getFileId()), file);
    }

    @Test
    public void downloadFileCallRepositoryMethod() {
        service.downloadFile(file.getFileId());
        Mockito.verify(repository, Mockito.atLeastOnce()).downloadFile(file.getFileId());
    }

    @Test
    public void editFileTitleOK() {
        Assertions.assertEquals(service.editFileTitle(file.getFileId()), "File's title edited successfully!");
    }

    @Test
    public void editFileTitleCallRepositoryMethod() {
        service.editFileTitle(file.getFileId());
        Mockito.verify(repository, Mockito.atLeastOnce()).editFileTitle(file.getFileId());
    }

    @Test
    public void getAllFilesOK() {
        Assertions.assertTrue(service.getAllFiles().contains(file));
    }

    @Test
    public void getAllFilesCallRepositoryMethod() {
        service.getAllFiles();
        Mockito.verify(repository, Mockito.atLeastOnce()).getAllFiles();
    }

}
