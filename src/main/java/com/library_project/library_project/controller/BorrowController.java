package com.library_project.library_project.controller;

import com.library_project.library_project.entity.BorrowingRecord;
import com.library_project.library_project.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/my")
    public String myBorrows(Model model, Principal principal) {
        List<BorrowingRecord> borrows = borrowService.getMyBorrowedBooks(principal.getName());
        model.addAttribute("borrows", borrows);
        return "borrow/my"; // Thymeleaf view
    }

    @PostMapping("/{bookId}")
    public String borrowBook(@PathVariable Long bookId, Principal principal) {
        boolean success = borrowService.borrowBook(principal.getName(), bookId);
        return "redirect:/books?borrow=" + (success ? "success" : "failed");
    }

    @PostMapping("/return/{borrowId}")
    public String returnBook(@PathVariable Long borrowId) {
        borrowService.returnBook(borrowId);
        return "redirect:/borrow/my";
    }
}
