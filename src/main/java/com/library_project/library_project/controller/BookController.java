package com.library_project.library_project.controller;

import com.library_project.library_project.entity.Book;
import com.library_project.library_project.repository.BookRepository;
import com.library_project.library_project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public String listBooks(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Create a Pageable object with sorting by title ascending
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<Book> booksPage = bookService.getAllBooks(search, pageable);

        model.addAttribute("booksPage", booksPage);
        model.addAttribute("search", search);
        return "books/list"; // points to src/main/resources/templates/books/list.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @PostMapping
    public String save(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/upload")
    public String uploadCover(@PathVariable Long id,
                              @RequestParam("file") MultipartFile file) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setCoverImage(file.getBytes());
        book.setCoverImageType(file.getContentType());
        bookRepository.save(book);

        return "redirect:/admin"; // or wherever you want to go after upload
    }


    @GetMapping("/{id}/cover")
    @ResponseBody
    public ResponseEntity<byte[]> serveCoverImage(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null || book.getCoverImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, book.getCoverImageType())
                .body(book.getCoverImage());
    }


}
