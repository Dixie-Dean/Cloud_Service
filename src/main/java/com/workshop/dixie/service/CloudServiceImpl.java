package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.repository.CloudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CloudServiceImpl implements CloudService {

    private final CloudRepository repository;

    public CloudServiceImpl(CloudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> uploadFile(UserFile userFile) {
        return repository.uploadFile(userFile);
    }

    @Override
    public Optional<String> deleteFile(String fileName) {
        return repository.deleteFile(fileName);
    }

    @Override
    public Optional<UserFile> downloadFile(String fileName) {
        return repository.downloadFile(fileName);
    }

    @Override
    public Optional<String> editFileName(String oldFileName, String newFileName) {
        return repository.editFileName(oldFileName, newFileName);
    }

    @Override
    public List<UserFile> getAllFiles(int limit) {
        return repository.getAllFiles(limit);
    }
}
