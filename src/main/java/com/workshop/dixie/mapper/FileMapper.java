package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.ResponseFileDTO;

import java.nio.charset.StandardCharsets;

public class FileMapper {

    public ResponseFileDTO toResponseDTO(File file) {
        if (file == null) {
            return null;
        }

        ResponseFileDTO destination = new ResponseFileDTO();
        destination.setFilename(file.getFilename());
        byte[] bytes = file.getFile().getBytes(StandardCharsets.UTF_8);
        destination.setSize(bytes.length);
        return destination;
    }
}
