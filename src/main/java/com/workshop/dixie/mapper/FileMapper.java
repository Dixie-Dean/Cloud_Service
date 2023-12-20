package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;

public class FileMapper {

    public FileDTO toUserFileDTO(File file) {
        if (file == null) {
            return null;
        }
        FileDTO destination = new FileDTO();
        destination.setHash(file.getHash());
//        destination.setFile(file.getFile().to);
        return destination;
    }
    public File toUserFile(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        }
        File destination = new File();
        destination.setHash(fileDTO.getHash());
        destination.setFile(fileDTO.getFile().toString());
        return destination;
    }
}
