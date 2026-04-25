package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.AssetCategory;
import com.example.examsystembackend.service.AssetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset/category")
@RequiredArgsConstructor
public class AssetCategoryController {

    private final AssetCategoryService assetCategoryService;

    @GetMapping
    public List<AssetCategory> getAllCategories() {
        return assetCategoryService.list();
    }

    @GetMapping("/{id}")
    public AssetCategory getCategoryById(@PathVariable Long id) {
        return assetCategoryService.getById(id);
    }

    @PostMapping
    public boolean createCategory(@RequestBody AssetCategory category) {
        return assetCategoryService.save(category);
    }

    @PutMapping("/{id}")
    public boolean updateCategory(@PathVariable Long id, @RequestBody AssetCategory category) {
        category.setId(id);
        return assetCategoryService.updateById(category);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable Long id) {
        return assetCategoryService.removeById(id);
    }
}