package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.ResponseFileDTO;
import org.springframework.util.SerializationUtils;

public class FileMapper {

    public ResponseFileDTO toResponseDTO(File file) {
        if (file == null) {
            return null;
        }

        ResponseFileDTO destination = new ResponseFileDTO();
        destination.setFilename(file.getFilename());
        byte[] bytes = SerializationUtils.serialize(file);
        if (bytes != null) {
            destination.setSize(bytes.length);
        }
        return destination;
    }
}
