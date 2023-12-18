package com.workshop.dixie.service;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;

import java.util.List;
import java.util.Optional;

public interface CloudService {
    Optional<String> uploadFile(FileDTO fileDTO);

    Optional<String> deleteFile(String fileName);

    Optional<File> downloadFile(String filename);

    Optional<String> editFileName(String oldFileName, String newFileName);

    List<FileDTO> getAllFiles(int limit);
}
