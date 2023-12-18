package com.workshop.dixie.entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {
    private String filename;
    private long userId;
    private CloudUserDTO cloudUserDTO;
}
