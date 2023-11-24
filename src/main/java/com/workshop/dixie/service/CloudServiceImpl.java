package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.repository.CloudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudServiceImpl implements CloudService {

    private final CloudRepository repository;

    public CloudServiceImpl(CloudRepository repository) {
        this.repository = repository;
    }

    @Override
    public String uploadFile(UserFile userFile) {
        return repository.uploadFile(userFile);
    }

    @Override
    public String deleteFile(String fileName) {
        return repository.deleteFile(fileName);
    }

    @Override
    public UserFile downloadFile(String fileName) {
        return repository.downloadFile(fileName);
    }

    @Override
    public String editFileTitle(String fileName) {
        return repository.editFileTitle(fileName);
    }

    @Override
    public List<UserFile> getAllFiles(int numberRequestedItems) {
        return null;
    }
}
