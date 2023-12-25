package com.workshop.dixie.service;

import com.workshop.dixie.entity.EditFileDTO;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public interface CloudService {
    ResponseEntity<String> uploadFile(String token, String filename, InputFileDTO inputFileDTO) throws InternalServerException, UnauthorizedException, BadCredentialsException, ErrorInputDataException;

    ResponseEntity<String> deleteFile(String token, String filename) throws UnauthorizedException, InternalServerException;

    ResponseEntity<ResponseFileDTO> downloadFile(String token, String filename) throws InternalServerException, UnauthorizedException;

    ResponseEntity<String> editFileName(String token, String filename, EditFileDTO editFileDTO) throws InternalServerException, UnauthorizedException;

    List<ResponseFileDTO> getAllFiles(String token, int limit) throws UnauthorizedException, InternalServerException, ErrorInputDataException;
}
