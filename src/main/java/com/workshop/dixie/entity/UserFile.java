package com.workshop.dixie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "cloud_schema", name = "files")
@Entity
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "cloud_user_id", referencedColumnName = "user_id")
    private CloudUser cloudUser;
}
