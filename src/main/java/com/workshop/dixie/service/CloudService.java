package com.workshop.dixie.service;

import com.workshop.dixie.entity.UserFile;

import java.util.List;

public interface CloudService {
    String uploadFile(UserFile userFile);

    String deleteFile(String fileName);

    UserFile downloadFile(String fileName);

    String editFileTitle(String fileName);

    List<UserFile> getAllFiles(int numberRequestedItems);
}
