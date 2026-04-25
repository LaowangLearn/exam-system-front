package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Archive;
import com.example.examsystembackend.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @GetMapping
    public List<Archive> getAllArchives() {
        return archiveService.list();
    }

    @GetMapping("/{id}")
    public Archive getArchiveById(@PathVariable Long id) {
        return archiveService.getById(id);
    }

    @PostMapping
    public boolean createArchive(@RequestBody Archive archive) {
        return archiveService.save(archive);
    }

    @PutMapping("/{id}")
    public boolean updateArchive(@PathVariable Long id, @RequestBody Archive archive) {
        archive.setId(id);
        return archiveService.updateById(archive);
    }

    @DeleteMapping("/{id}")
    public boolean deleteArchive(@PathVariable Long id) {
        return archiveService.removeById(id);
    }
}