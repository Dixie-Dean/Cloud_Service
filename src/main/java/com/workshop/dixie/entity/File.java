package com.workshop.dixie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(schema = "cloud_schema", name = "files")
@Entity
public class File implements Serializable {

    @Id
    private String hash;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "file")
    private String file;

    @Column(name = "email", nullable = false)
    private String email;
}
