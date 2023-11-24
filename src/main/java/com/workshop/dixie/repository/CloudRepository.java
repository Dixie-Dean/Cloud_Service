package com.workshop.dixie.repository;

import com.workshop.dixie.entity.UserFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CloudRepository {

    public String uploadFile(UserFile userFile) {
        return null;
    }

    public String deleteFile(String fileName) {
        return null;
    }

    public UserFile downloadFile(String fileName) {
        return null;
    }

    public String editFileTitle(String fileName) {
        return null;
    }

    public List<UserFile> getAllFiles(int numberRequestedItems) {
        return null;
    }
}
