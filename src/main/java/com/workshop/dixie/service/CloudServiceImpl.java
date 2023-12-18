package com.workshop.dixie.service;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.repository.CloudFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CloudServiceImpl implements CloudService {
    private final CloudFileRepository cloudFileRepository;
    private FileMapper fileMapper;

    public CloudServiceImpl(CloudFileRepository cloudFileRepository, FileMapper fileMapper) {
        this.cloudFileRepository = cloudFileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public Optional<String> uploadFile(FileDTO fileDTO) {
        Optional<String> response = cloudFileRepository.uploadFile(fileDTO.getFilename(), fileDTO.getUserId());
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public Optional<String> deleteFile(String fileName) {
        Optional<String> response = cloudFileRepository.deleteFile(fileName);
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public Optional<File> downloadFile(String filename) {
        return cloudFileRepository.downloadFile(filename);
    }

    @Override
    public Optional<String> editFileName(String oldFileName, String newFileName) {
        Optional<String> response = cloudFileRepository.editFileName(oldFileName, newFileName);
        return response.isPresent() ? response : Optional.of("Error Input Data");
    }

    @Override
    public List<FileDTO> getAllFiles(int limit) {
        List<File> fileList = cloudFileRepository.getAllFiles(limit);
        List<FileDTO> fileDtoList = new CopyOnWriteArrayList<>();

        for (File file : fileList) {
            fileDtoList.add(fileMapper.toUserFileDTO(file));
        }

        return fileDtoList;
    }
}
