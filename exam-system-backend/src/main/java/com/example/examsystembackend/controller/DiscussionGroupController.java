package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.DiscussionGroup;
import com.example.examsystembackend.service.DiscussionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class DiscussionGroupController {

    private final DiscussionGroupService discussionGroupService;

    @GetMapping
    public List<DiscussionGroup> getAllGroups() {
        return discussionGroupService.list();
    }

    @GetMapping("/{id}")
    public DiscussionGroup getGroupById(@PathVariable Long id) {
        return discussionGroupService.getById(id);
    }

    @PostMapping
    public boolean createGroup(@RequestBody DiscussionGroup group) {
        return discussionGroupService.save(group);
    }

    @PutMapping("/{id}")
    public boolean updateGroup(@PathVariable Long id, @RequestBody DiscussionGroup group) {
        group.setId(id);
        return discussionGroupService.updateById(group);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGroup(@PathVariable Long id) {
        return discussionGroupService.removeById(id);
    }
}