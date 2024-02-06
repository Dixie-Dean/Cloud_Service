package com.workshop.dixie.service;

import com.workshop.dixie.dto.EditFileDTO;
import com.workshop.dixie.dto.InputFileDTO;
import com.workshop.dixie.dto.ResponseFileDTO;
import com.workshop.dixie.entity.File;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.mapper.FileMapper;
import com.workshop.dixie.repository.CloudFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CloudServiceImpl implements CloudService {
    private final CloudFileRepository cloudFileRepository;
    private final FileMapper fileMapper;

    public CloudServiceImpl(CloudFileRepository cloudFileRepository,
                            FileMapper fileMapper) {
        this.cloudFileRepository = cloudFileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public ResponseEntity<String> uploadFile(String filename, InputFileDTO inputFileDTO)
            throws InternalServerException, ErrorInputDataException {
        if (inputFileDTO.getFile().isEmpty()) {
            throw new ErrorInputDataException("Incorrect input data");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> response = cloudFileRepository.uploadFile(
                UUID.randomUUID().toString(),
                filename,
                authentication.getName(),
                inputFileDTO.getFile().toString());

        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to upload file"));
    }

    @Override
    public ResponseEntity<String> deleteFile(String filename) throws InternalServerException {
        Optional<String> response = cloudFileRepository.deleteFile(filename);
        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to delete file"));
    }

    @Override
    public ResponseEntity<ResponseFileDTO> downloadFile(String filename) throws InternalServerException {
        Optional<File> downloadedFile = cloudFileRepository.downloadFile(filename);
        return downloadedFile.map(file ->
                new ResponseEntity<>(fileMapper.turnIntoDTO(file), HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to download file"));
    }

    @Override
    public ResponseEntity<String> editFileName(String filename, EditFileDTO editFileDTO) throws InternalServerException {
        Optional<String> response = cloudFileRepository.editFileName(filename, editFileDTO.getName());
        return response.map(string ->
                new ResponseEntity<>(string, HttpStatus.OK)).orElseThrow(() ->
                new InternalServerException("Unable to edit filename"));
    }

    @Override
    public List<ResponseFileDTO> getAllFiles(int limit) throws ErrorInputDataException {
        if (limit < 0) {
            throw new ErrorInputDataException("Incorrect input data");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<File> fileList = cloudFileRepository.getAllFiles(limit, authentication.getName());

        List<ResponseFileDTO> fileDtoList = new CopyOnWriteArrayList<>();
        for (File file : fileList) {
            fileDtoList.add(fileMapper.turnIntoDTO(file));
        }
        return fileDtoList;
    }
}
