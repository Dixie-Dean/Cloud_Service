package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;

public class FileMapper {
    private final CloudUserMapper cloudUserMapper;

    public FileMapper(CloudUserMapper cloudUserMapper) {
        this.cloudUserMapper = cloudUserMapper;
    }

    public FileDTO toUserFileDTO(File file) {
        if (file == null) {
            return null;
        }
        FileDTO destination = new FileDTO();
        destination.setFilename(file.getFilename());
        destination.setUserId(file.getFileId());
        destination.setCloudUserDTO(cloudUserMapper.toCloudUserDTO(file.getCloudUser()));
        return destination;
    }
    public File toUserFile(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        }
        File destination = new File();
        destination.setFilename(fileDTO.getFilename());
        destination.setFileId(fileDTO.getUserId());
        destination.setCloudUser(cloudUserMapper.toCloudUser(fileDTO.getCloudUserDTO()));
        return destination;
    }
}
