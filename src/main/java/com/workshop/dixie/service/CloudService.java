package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CloudService {
    ResponseEntity<String> uploadFile(UserFile userFile);

    ResponseEntity<String> deleteFile(UUID fileId);

    UserFile downloadFile(UUID fileId);

    ResponseEntity<String> editFileTitle(UUID fileId);

    List<UserFile> getAllFiles();
}
