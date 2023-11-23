package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.repository.CloudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CloudServiceImpl implements CloudService {

    private final CloudRepository repository;

    public CloudServiceImpl(CloudRepository repository) {
        this.repository = repository;
    }

    @Override
    public String uploadFile(UserFile userFile) {
        return null;
    }

    @Override
    public String deleteFile(UUID fileId) {
        return null;
    }

    @Override
    public UserFile downloadFile(UUID fileId) {
        return null;
    }

    @Override
    public String editFileTitle(UUID fileId) {
        return null;
    }

    @Override
    public List<UserFile> getAllFiles() {
        return null;
    }
}
