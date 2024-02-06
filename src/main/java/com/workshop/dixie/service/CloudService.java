package com.workshop.dixie.service;

import com.workshop.dixie.dto.EditFileDTO;
import com.workshop.dixie.dto.InputFileDTO;
import com.workshop.dixie.dto.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public interface CloudService {
    ResponseEntity<String> uploadFile(String filename, InputFileDTO inputFileDTO) throws InternalServerException, UnauthorizedException, BadCredentialsException, ErrorInputDataException;

    ResponseEntity<String> deleteFile(String filename) throws UnauthorizedException, InternalServerException;

    ResponseEntity<ResponseFileDTO> downloadFile(String filename) throws InternalServerException, UnauthorizedException;

    ResponseEntity<String> editFileName(String filename, EditFileDTO editFileDTO) throws InternalServerException, UnauthorizedException;

    List<ResponseFileDTO> getAllFiles(int limit) throws UnauthorizedException, InternalServerException, ErrorInputDataException;
}
