package com.workshop.dixie.controller;

import com.workshop.dixie.entity.UserFile;
import com.workshop.dixie.service.CloudService;
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

    @PostMapping("/upload")
    public Optional<String> uploadFile(@RequestBody UserFile userFile) {
        return cloudService.uploadFile(userFile);
    }

    @DeleteMapping("/delete")
    public Optional<String> deleteFile(@RequestParam String fileName) {
        return cloudService.deleteFile(fileName);
    }

    @GetMapping("/download")
    public Optional<UserFile> downloadFile(@RequestParam String filename) {
        return cloudService.downloadFile(filename);
    }

    @PutMapping("/edit")
    public Optional<String> editFileName(@RequestBody String oldFileName, @RequestParam String newFileName) {
        return cloudService.editFileName(oldFileName, newFileName);
    }

    @GetMapping("/list")
    public List<UserFile> getAllFiles(@RequestParam int limit) {
        return cloudService.getAllFiles(limit);
    }
}
