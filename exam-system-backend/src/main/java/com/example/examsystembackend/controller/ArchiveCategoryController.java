package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.ArchiveCategory;
import com.example.examsystembackend.service.ArchiveCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive/category")
@RequiredArgsConstructor
public class ArchiveCategoryController {

    private final ArchiveCategoryService archiveCategoryService;

    @GetMapping
    public List<ArchiveCategory> getAllCategories() {
        return archiveCategoryService.list();
    }

    @GetMapping("/{id}")
    public ArchiveCategory getCategoryById(@PathVariable Long id) {
        return archiveCategoryService.getById(id);
    }

    @PostMapping
    public boolean createCategory(@RequestBody ArchiveCategory category) {
        return archiveCategoryService.save(category);
    }

    @PutMapping("/{id}")
    public boolean updateCategory(@PathVariable Long id, @RequestBody ArchiveCategory category) {
        category.setId(id);
        return archiveCategoryService.updateById(category);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable Long id) {
        return archiveCategoryService.removeById(id);
    }
}