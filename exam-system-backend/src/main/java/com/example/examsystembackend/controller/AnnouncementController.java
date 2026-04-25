package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Announcement;
import com.example.examsystembackend.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public List<Announcement> getAllAnnouncements() {
        return announcementService.list();
    }

    @GetMapping("/{id}")
    public Announcement getAnnouncementById(@PathVariable Long id) {
        return announcementService.getById(id);
    }

    @PostMapping
    public boolean createAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.save(announcement);
    }

    @PutMapping("/{id}")
    public boolean updateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {
        announcement.setId(id);
        return announcementService.updateById(announcement);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAnnouncement(@PathVariable Long id) {
        return announcementService.removeById(id);
    }
}