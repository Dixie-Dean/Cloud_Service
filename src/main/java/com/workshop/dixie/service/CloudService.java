package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;

import java.util.List;
import java.util.UUID;

public interface CloudService {
    String uploadFile(UserFile userFile);

    String deleteFile(UUID fileId);

    UserFile downloadFile(UUID fileId);

    String editFileTitle(UUID fileId);

    List<UserFile> getAllFiles();
}
