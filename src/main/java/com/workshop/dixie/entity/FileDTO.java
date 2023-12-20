package com.workshop.dixie.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO implements MultipartFile {
    private String filename;
//    private long userId;
//    private CloudUserDTO cloudUserDTO;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
