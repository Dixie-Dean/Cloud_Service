package com.workshop.dixie.mapper;

import com.workshop.dixie.entity.CloudUser;
import com.workshop.dixie.entity.CloudUserDTO;

public class CloudUserMapper {
    public CloudUserDTO toCloudUserDTO(CloudUser cloudUser) {
        if (cloudUser == null) {
            return null;
        }
        CloudUserDTO cloudUserDTO = new CloudUserDTO();
        cloudUserDTO.setUserId(cloudUser.getUserId());
        cloudUserDTO.setUsername(cloudUser.getUsername());
        cloudUserDTO.setLastname(cloudUser.getLastname());
        cloudUserDTO.setEmail(cloudUser.getEmail());
        cloudUserDTO.setPassword(cloudUser.getPassword());
        cloudUserDTO.setRoles(cloudUser.getRoles());
        return cloudUserDTO;
    }

    public CloudUser toCloudUser(CloudUserDTO cloudUserDTO) {
        if (cloudUserDTO == null) {
            return null;
        }
        CloudUser cloudUser = new CloudUser();
        cloudUser.setUserId(cloudUserDTO.getUserId());
        cloudUser.setUsername(cloudUserDTO.getUsername());
        cloudUser.setLastname(cloudUserDTO.getLastname());
        cloudUser.setEmail(cloudUserDTO.getEmail());
        cloudUser.setPassword(cloudUserDTO.getPassword());
        cloudUser.setRoles(cloudUserDTO.getRoles());
        return cloudUser;
    }
}
