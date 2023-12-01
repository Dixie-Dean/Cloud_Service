package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.entity.UserFileDTO;

import java.util.List;
import java.util.Optional;

public interface CloudService {
    Optional<String> uploadFile(UserFileDTO userFileDTO);

    Optional<String> deleteFile(String fileName);

    Optional<UserFile> downloadFile(String filename);

    Optional<String> editFileName(String oldFileName, String newFileName);

    List<UserFile> getAllFiles(int limit);
}
