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
        return null;
    }

    @Override
    public String deleteFile(String fileName) {
        return null;
    }

    @Override
    public UserFile downloadFile(String fileName) {
        return null;
    }

    @Override
    public String editFileTitle(String fileName) {
        return null;
    }

    @Override
    public List<UserFile> getAllFiles() {
        return null;
    }
}
