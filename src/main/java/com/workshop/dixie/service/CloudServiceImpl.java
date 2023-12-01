package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.entity.UserFileDTO;
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
    public Optional<String> uploadFile(UserFileDTO userFileDTO) {
        Optional<String> response = repository.uploadFile(userFileDTO.getFileName(), userFileDTO.getUserId());
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public Optional<String> deleteFile(String fileName) {
        Optional<String> response = repository.deleteFile(fileName);
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public Optional<UserFile> downloadFile(String filename) {
        return repository.downloadFile(filename);
    }

    @Override
    public Optional<String> editFileName(String oldFileName, String newFileName) {
        Optional<String> response = repository.editFileName(oldFileName, newFileName);
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public List<UserFile> getAllFiles(int limit) {
        return repository.getAllFiles(limit);
    }
}
