package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import org.springframework.util.SerializationUtils;

public class FileMapper {

    public InputFileDTO toUserFileDTO(File file) {
        if (file == null) {
            return null;
        }
        InputFileDTO destination = new InputFileDTO();
        destination.setHash(file.getHash());
//        destination.setFile(file.getFile().to);
        return destination;
    }

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

    public File toUserFile(InputFileDTO inputFileDTO) {
        if (inputFileDTO == null) {
            return null;
        }
        File destination = new File();
        destination.setHash(inputFileDTO.getHash());
        destination.setFile(inputFileDTO.getFile().toString());
        return destination;
    }
}
