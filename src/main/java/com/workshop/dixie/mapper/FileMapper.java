package com.workshop.dixie.mapper;

import com.workshop.dixie.application.model.dto.ResponseFileDTO;
import com.workshop.dixie.application.model.entity.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    ResponseFileDTO turnIntoDTO (File file);
}
