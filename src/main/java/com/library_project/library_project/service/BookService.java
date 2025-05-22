package com.library_project.library_project.service;

import com.library_project.library_project.entity.Book;
import com.library_project.library_project.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    public Page<Book> getAllBooks(String search, Pageable pageable) {
        Pageable effectivePageable = pageable != null ? pageable : Pageable.unpaged();

        if (search == null || search.trim().isEmpty()) {
            return bookRepository.findAll(effectivePageable);
        }
        return bookRepository.findByTitleContainingIgnoreCase(search, effectivePageable);
    }


    public void save(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

//    public void uploadCover(Long bookId, MultipartFile file) throws IOException {
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new RuntimeException("Book not found"));
//
//        if (!file.isEmpty()) {
//            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
//            Path uploadPath = Paths.get("src/main/uploads/");
//            Files.createDirectories(uploadPath);
//            Path filePath = uploadPath.resolve(filename);
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            book.setCoverImagePath("/uploads/" + filename); // relative URL for the browser
//            bookRepository.save(book);
//        }
//    }

}

