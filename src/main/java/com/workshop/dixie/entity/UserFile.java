package com.workshop.dixie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID fileId;

    @Column(nullable = false)
    private String title;
}
