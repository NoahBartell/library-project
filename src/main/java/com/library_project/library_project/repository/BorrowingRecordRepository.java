package com.library_project.library_project.repository;

import com.library_project.library_project.entity.BorrowingRecord;
import com.library_project.library_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByUserUsername(String username);

    List<BorrowingRecord> findByUserAndReturnDateIsNull(User user);
}

