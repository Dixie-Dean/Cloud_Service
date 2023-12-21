package com.workshop.dixie.controller;

import com.workshop.dixie.entity.EditFileDTO;
import com.workshop.dixie.entity.InputFileDTO;
import com.workshop.dixie.entity.ResponseFileDTO;
import com.workshop.dixie.service.CloudService;
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

    @PostMapping(value = "/file")
    public ResponseEntity<String> uploadFile(@RequestHeader(name = "auth-token") String token,
                                             @RequestParam String filename,
                                             @ModelAttribute InputFileDTO file) {
        return cloudService.uploadFile(token, filename, file);
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader(name = "auth-token") String token,
                                             @RequestParam String filename) {
        return cloudService.deleteFile(token, filename);
    }

    @GetMapping("/file")
    public ResponseEntity<ResponseFileDTO> downloadFile(@RequestHeader(name = "auth-token") String token,
                                                        @RequestParam String filename) {
        return cloudService.downloadFile(token, filename);
    }

    @PutMapping("/file")
    public ResponseEntity<String> editFileName(@RequestHeader(name = "auth-token") String token,
                                               @RequestParam String filename,
                                               @RequestBody EditFileDTO editFileDTO) {
        return cloudService.editFileName(token, filename, editFileDTO);
    }

    @GetMapping("/list")
    public List<ResponseFileDTO> getAllFiles(@RequestHeader(name = "auth-token") String token,
                                             @RequestParam int limit) {
        return cloudService.getAllFiles(token, limit);
    }
}
