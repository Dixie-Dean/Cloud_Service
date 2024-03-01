package com.workshop.dixie.application.model.entity;

import com.workshop.dixie.security.authentication.model.entity.CloudUser;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private CloudUser cloudUser;
}
