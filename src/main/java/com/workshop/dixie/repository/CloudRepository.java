package com.workshop.dixie.repository;

import com.workshop.dixie.entity.UserFile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CloudRepository {

    public String uploadFile(UserFile userFile) {
        return null;
    }

    public String deleteFile(UUID fileId) {
        return null;
    }

    public UserFile downloadFile(UUID fileId) {
        return null;
    }

    public String editFileTitle(UUID fileId) {
        return null;
    }

    public List<String> getAllFiles() {
        return null;
    }
}
