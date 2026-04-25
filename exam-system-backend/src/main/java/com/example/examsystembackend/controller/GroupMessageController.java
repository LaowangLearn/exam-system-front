package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.GroupMessage;
import com.example.examsystembackend.service.GroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-message")
@RequiredArgsConstructor
public class GroupMessageController {

    private final GroupMessageService groupMessageService;

    @GetMapping
    public List<GroupMessage> getAllMessages() {
        return groupMessageService.list();
    }

    @GetMapping("/{id}")
    public GroupMessage getMessageById(@PathVariable Long id) {
        return groupMessageService.getById(id);
    }

    @PostMapping
    public boolean createMessage(@RequestBody GroupMessage message) {
        return groupMessageService.save(message);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMessage(@PathVariable Long id) {
        return groupMessageService.removeById(id);
    }
}