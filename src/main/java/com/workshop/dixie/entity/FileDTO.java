package com.workshop.dixie.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {
    private String hash;
    private MultipartFile file;
}
