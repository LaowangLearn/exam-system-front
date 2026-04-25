package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.ArchiveBorrow;
import com.example.examsystembackend.service.ArchiveBorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive/borrow")
@RequiredArgsConstructor
public class ArchiveBorrowController {

    private final ArchiveBorrowService archiveBorrowService;

    @GetMapping
    public List<ArchiveBorrow> getAllBorrows() {
        return archiveBorrowService.list();
    }

    @GetMapping("/{id}")
    public ArchiveBorrow getBorrowById(@PathVariable Long id) {
        return archiveBorrowService.getById(id);
    }

    @PostMapping
    public boolean createBorrow(@RequestBody ArchiveBorrow borrow) {
        return archiveBorrowService.save(borrow);
    }

    @PutMapping("/{id}")
    public boolean updateBorrow(@PathVariable Long id, @RequestBody ArchiveBorrow borrow) {
        borrow.setId(id);
        return archiveBorrowService.updateById(borrow);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBorrow(@PathVariable Long id) {
        return archiveBorrowService.removeById(id);
    }
}