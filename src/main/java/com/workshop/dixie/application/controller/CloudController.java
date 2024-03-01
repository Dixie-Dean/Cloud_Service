package com.workshop.dixie.application.controller;

import com.workshop.dixie.application.model.dto.EditFileDTO;
import com.workshop.dixie.application.model.dto.InputFileDTO;
import com.workshop.dixie.application.model.dto.ResponseFileDTO;
import com.workshop.dixie.exception.ErrorInputDataException;
import com.workshop.dixie.exception.InternalServerException;
import com.workshop.dixie.application.service.CloudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloud")
public class CloudController {
    private final CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam String filename, @ModelAttribute InputFileDTO file)
            throws ErrorInputDataException, InternalServerException {
        return cloudService.uploadFile(filename, file);
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String filename)
            throws InternalServerException {
        return cloudService.deleteFile(filename);
    }

    @GetMapping("/file")
    public ResponseEntity<ResponseFileDTO> downloadFile(@RequestParam String filename)
            throws InternalServerException {
        return cloudService.downloadFile(filename);
    }

    @PutMapping("/file")
    public ResponseEntity<String> editFileName(@RequestParam String filename, @RequestBody EditFileDTO editFileDTO)
            throws InternalServerException {
        return cloudService.editFileName(filename, editFileDTO);
    }

    @GetMapping("/list")
    public List<ResponseFileDTO> getAllFiles(@RequestParam int limit)
            throws ErrorInputDataException, InternalServerException {
        return cloudService.getAllFiles(limit);
    }
}
