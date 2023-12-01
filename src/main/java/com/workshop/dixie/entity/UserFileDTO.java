package com.workshop.dixie.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFileDTO {
    private String fileName;
    private long userId;
}
