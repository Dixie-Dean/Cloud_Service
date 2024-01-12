package com.workshop.dixie.service;

import com.workshop.dixie.entity.EditFileDTO;
import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.exception.UnauthorizedException;
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
    public ResponseEntity<String> uploadFile(String token, String filename, InputFileDTO inputFileDTO)
            throws InternalServerException, UnauthorizedException, ErrorInputDataException {
        if (tokenManager.validateToken(token)) {
            throw new UnauthorizedException("Token is expired or incorrect");
        } else if (inputFileDTO.getFile().isEmpty()) {
            throw new ErrorInputDataException("Incorrect input data");
        }

        Optional<String> response = cloudFileRepository.uploadFile(
                UUID.randomUUID().toString(),
                filename,
                tokenManager.extractEmailFromJwt(token),
                inputFileDTO.getFile().toString());

        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to upload file"));
    }

    @Override
    public ResponseEntity<String> deleteFile(String token, String filename)
            throws UnauthorizedException, InternalServerException {
        if (tokenManager.validateToken(token)) {
            throw new UnauthorizedException("Token is expired or incorrect");
        }

        Optional<String> response = cloudFileRepository.deleteFile(filename);
        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to delete file"));
    }

    @Override
    public ResponseEntity<ResponseFileDTO> downloadFile(String token, String filename)
            throws InternalServerException, UnauthorizedException {
        if (tokenManager.validateToken(token)) {
            throw new UnauthorizedException("Token is expired or incorrect");
        }

        Optional<File> downloadedFile = cloudFileRepository.downloadFile(filename);
        return downloadedFile.map(file ->
                new ResponseEntity<>(fileMapper.toResponseDTO(file), HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to download file"));
    }

    @Override
    public ResponseEntity<String> editFileName(String token, String filename, EditFileDTO editFileDTO)
            throws InternalServerException, UnauthorizedException {
        if (tokenManager.validateToken(token)) {
            throw new UnauthorizedException("Token is expired or incorrect");
        }

        Optional<String> response = cloudFileRepository.editFileName(filename, editFileDTO.getName());

        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to edit filename"));
    }

    @Override
    public List<ResponseFileDTO> getAllFiles(String token, int limit)
            throws UnauthorizedException, ErrorInputDataException {
        if (tokenManager.validateToken(token)) {
            throw new UnauthorizedException("Token is expired or incorrect");
        } else if (limit < 0) {
            throw new ErrorInputDataException("Incorrect input data");
        }

        String username = tokenManager.extractEmailFromJwt(token);
        List<File> fileList = cloudFileRepository.getAllFiles(limit, username);

        List<ResponseFileDTO> fileDtoList = new CopyOnWriteArrayList<>();
        for (File file : fileList) {
            fileDtoList.add(fileMapper.toResponseDTO(file));
        }
        return fileDtoList;
    }
}
