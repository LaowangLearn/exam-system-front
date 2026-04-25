package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.InternalMessage;
import com.example.examsystembackend.service.InternalMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class InternalMessageController {

    private final InternalMessageService internalMessageService;

    @GetMapping
    public List<InternalMessage> getAllMessages() {
        return internalMessageService.list();
    }

    @GetMapping("/{id}")
    public InternalMessage getMessageById(@PathVariable Long id) {
        return internalMessageService.getById(id);
    }

    @PostMapping
    public boolean createMessage(@RequestBody InternalMessage message) {
        return internalMessageService.save(message);
    }

    @PutMapping("/{id}")
    public boolean updateMessage(@PathVariable Long id, @RequestBody InternalMessage message) {
        message.setId(id);
        return internalMessageService.updateById(message);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMessage(@PathVariable Long id) {
        return internalMessageService.removeById(id);
    }
}