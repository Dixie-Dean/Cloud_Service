package com.workshop.dixie.application.service;

import com.workshop.dixie.application.model.dto.EditFileDTO;
import com.workshop.dixie.application.model.dto.InputFileDTO;
import com.workshop.dixie.application.model.dto.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public interface CloudService {
    ResponseEntity<String> uploadFile(String filename, InputFileDTO inputFileDTO) throws InternalServerException, BadCredentialsException, ErrorInputDataException;

    ResponseEntity<String> deleteFile(String filename) throws InternalServerException;

    ResponseEntity<ResponseFileDTO> downloadFile(String filename) throws InternalServerException;

    ResponseEntity<String> editFileName(String filename, EditFileDTO editFileDTO) throws InternalServerException;

    List<ResponseFileDTO> getAllFiles(int limit) throws InternalServerException, ErrorInputDataException;
}
