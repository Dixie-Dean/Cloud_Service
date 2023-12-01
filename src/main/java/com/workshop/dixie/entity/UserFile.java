package com.workshop.dixie.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(schema = "cloud_schema", name = "files")
@Entity
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private CloudUser cloudUser;
}
