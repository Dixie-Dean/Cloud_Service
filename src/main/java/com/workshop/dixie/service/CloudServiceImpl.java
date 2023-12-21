package com.workshop.dixie.service;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.repository.CloudFileRepository;
import com.workshop.dixie.security.TokenManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CloudServiceImpl implements CloudService {
    private final CloudFileRepository cloudFileRepository;
    private final FileMapper fileMapper;
    private final TokenManager tokenManager;

    public CloudServiceImpl(CloudFileRepository cloudFileRepository,
                            FileMapper fileMapper,
                            TokenManager tokenManager) {
        this.cloudFileRepository = cloudFileRepository;
        this.fileMapper = fileMapper;
        this.tokenManager = tokenManager;
    }

    @Override
    public ResponseEntity<String> uploadFile(String token, String filename, InputFileDTO inputFileDTO) {
//        if (tokenManager.validateToken(token)) {
//            return new ResponseEntity<>("Token is expired or incorrect", HttpStatus.UNAUTHORIZED);
//        }

        Optional<String> response = cloudFileRepository.uploadFile(
                UUID.randomUUID().toString(),
                filename,
                inputFileDTO.getFile().toString());

        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>("Error Input Data", HttpStatus.BAD_REQUEST));
    }

    @Override
    public ResponseEntity<String> deleteFile(String token, String filename) {
//        if (tokenManager.validateToken(token)) {
//            return new ResponseEntity<>("Token is expired or incorrect", HttpStatus.UNAUTHORIZED);
//        }

        Optional<String> response = cloudFileRepository.deleteFile(filename);
        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>("Error Input Data", HttpStatus.BAD_REQUEST));
    }

    @Override
    public Optional<File> downloadFile(String token, String filename) {
//        if (tokenManager.validateToken(token)) {
//            return Optional.empty();
//        }

        return cloudFileRepository.downloadFile(filename);
    }

    @Override
    public ResponseEntity<String> editFileName(String token, String filename, String newFileName) {
//        if (tokenManager.validateToken(token)) {
//            return new ResponseEntity<>("Token is expired or incorrect", HttpStatus.UNAUTHORIZED);
//        }

        Optional<String> response = cloudFileRepository.editFileName(filename, newFileName);
        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>("Error Input Data", HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<ResponseFileDTO> getAllFiles(String token, int limit) {
//        if (tokenManager.validateToken(token)) {
//            return null;
//        }

        List<File> fileList = cloudFileRepository.getAllFiles(limit);
        List<ResponseFileDTO> fileDtoList = new CopyOnWriteArrayList<>();

        for (File file : fileList) {
            fileDtoList.add(fileMapper.toResponseDTO(file));
        }

        return fileDtoList;
    }
}
