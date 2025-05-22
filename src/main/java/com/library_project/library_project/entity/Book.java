package com.library_project.library_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    @Lob
    @Column(name = "cover_image")
    private byte[] coverImage;

    @Column(name = "cover_image_type")
    private String coverImageType;

    private int quantity;
}
