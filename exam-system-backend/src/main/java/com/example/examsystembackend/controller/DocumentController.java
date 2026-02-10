package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Document;
import com.example.examsystembackend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oa/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.list();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentService.getById(id);
    }

    @PostMapping
    public boolean createDocument(@RequestBody Document document) {
        return documentService.save(document);
    }

    @PutMapping("/{id}")
    public boolean updateDocument(@PathVariable Long id, @RequestBody Document document) {
        document.setId(id);
        return documentService.updateById(document);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDocument(@PathVariable Long id) {
        return documentService.removeById(id);
    }
}
