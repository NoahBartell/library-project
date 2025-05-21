package com.library_project.library_project.controller;

import com.library_project.library_project.entity.Book;
import com.library_project.library_project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private BookService bookService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("books", bookService.getAllBooks(null, null).getContent()); // no pagination here
        return "admin/dashboard";
    }

    @GetMapping("/books/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/admin";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/admin";
    }
}
