package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;

import java.util.List;
import java.util.Optional;

public interface CloudService {
    Optional<String> uploadFile(UserFile userFile);

    Optional<String> deleteFile(String fileName);

    Optional<UserFile> downloadFile(String fileName);

    Optional<String> editFileName(String oldFileName, String newFileName);

    List<UserFile> getAllFiles(int limit);
}
