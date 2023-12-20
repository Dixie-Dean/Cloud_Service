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
        destination.setFilename(file.getFilename());
        destination.setFile(file.getFile());
        return destination;
    }
    public File toUserFile(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        }
        File destination = new File();
        destination.setHash(fileDTO.getHash());
        destination.setFilename(fileDTO.getFilename());
        destination.setFile(fileDTO.getFile());
        return destination;
    }
}
