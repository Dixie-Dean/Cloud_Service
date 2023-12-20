package com.workshop.dixie.controller;

import com.workshop.dixie.entity.File;
import com.workshop.dixie.entity.FileDTO;
import com.workshop.dixie.service.CloudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cloud")
public class CloudController {
    private final CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestHeader(name = "auth-token") String token,
                                             @RequestParam String filename,
                                             @ModelAttribute FileDTO fileDTO) {
        return cloudService.uploadFile(token, filename, fileDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestHeader(name = "auth-token") String token,
                                             @RequestParam String filename) {
        return cloudService.deleteFile(token, filename);
    }

    @GetMapping("/download")
    public Optional<File> downloadFile(@RequestHeader(name = "auth-token") String token,
                                       @RequestParam String fileName) {
        return cloudService.downloadFile(token, fileName);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editFileName(@RequestHeader(name = "auth-token") String token,
                                               @RequestParam String fileName,
                                               @RequestParam String newFileName) {
        return cloudService.editFileName(token, fileName, newFileName);
    }

    @GetMapping("/list")
    public List<FileDTO> getAllFiles(@RequestHeader(name = "auth-token") String token,
                                     @RequestParam int limit) {
        return cloudService.getAllFiles(token, limit);
    }
}
