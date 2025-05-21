package com.library_project.library_project.service;

import com.library_project.library_project.entity.Book;
import com.library_project.library_project.entity.BorrowingRecord;
import com.library_project.library_project.entity.User;
import com.library_project.library_project.repository.BookRepository;
import com.library_project.library_project.repository.BorrowingRecordRepository;
import com.library_project.library_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BorrowingRecordRepository borrowRepo;
    @Autowired private BookRepository bookRepo;
    @Autowired private UserRepository userRepo;

    public boolean borrowBook(String username, Long bookId) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        Optional<Book> bookOpt = bookRepo.findById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) return false;

        Book book = bookOpt.get();
        if (book.getQuantity() <= 0) return false;

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(userOpt.get());
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        borrowRepo.save(record);

        book.setQuantity(book.getQuantity() - 1);
        bookRepo.save(book);
        return true;
    }

    public boolean returnBook(Long borrowId) {
        Optional<BorrowingRecord> recordOpt = borrowRepo.findById(borrowId);
        if (recordOpt.isEmpty()) return false;

        BorrowingRecord record = recordOpt.get();
        Book book = record.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepo.save(book);

        record.setReturnDate(LocalDate.now());
        borrowRepo.save(record);
        return true;
    }

    public List<BorrowingRecord> getMyBorrowedBooks(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) return List.of();
        return borrowRepo.findByUserAndReturnDateIsNull(user.get());
    }
}
