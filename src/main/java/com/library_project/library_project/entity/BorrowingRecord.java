package com.library_project.library_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @ManyToOne
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    @ManyToOne private Book book;

    @Getter
    @Setter
    private LocalDate borrowDate;
    @Getter
    @Setter
    private LocalDate returnDate;
}

