package com.workshop.dixie.service;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CloudService {
    ResponseEntity<String> uploadFile(String token, String filename, FileDTO fileDTO);

    ResponseEntity<String> deleteFile(String token, String fileName);

    Optional<File> downloadFile(String token, String filename);

    ResponseEntity<String> editFileName(String token, String oldFileName, String newFileName);

    List<FileDTO> getAllFiles(String token, int limit);
}
