package com.workshop.dixie.service;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CloudService {
    ResponseEntity<String> uploadFile(String token, String filename, InputFileDTO inputFileDTO);

    ResponseEntity<String> deleteFile(String token, String filename);

    Optional<File> downloadFile(String token, String filename);

    ResponseEntity<String> editFileName(String token, String filename, String newFileName);

    List<ResponseFileDTO> getAllFiles(String token, int limit);
}
