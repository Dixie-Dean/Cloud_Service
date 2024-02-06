package com.workshop.dixie.mapper;

import com.workshop.dixie.dto.ResponseFileDTO;
import com.workshop.dixie.entity.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    ResponseFileDTO turnIntoDTO (File file);
}
