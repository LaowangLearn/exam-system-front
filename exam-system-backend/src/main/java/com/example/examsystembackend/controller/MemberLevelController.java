package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.MemberLevel;
import com.example.examsystembackend.service.MemberLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/member/levels")
@RequiredArgsConstructor
public class MemberLevelController {

    private final MemberLevelService memberLevelService;

    @GetMapping
    public List<MemberLevel> list() {
        return memberLevelService.listOrderBySort();
    }

    @GetMapping("/{id}")
    public MemberLevel getById(@PathVariable Long id) {
        return memberLevelService.getById(id);
    }

    @PostMapping
    public MemberLevel create(@RequestBody MemberLevel level) {
        level.setCreateTime(LocalDateTime.now());
        memberLevelService.save(level);
        return level;
    }

    @PutMapping("/{id}")
    public MemberLevel update(@PathVariable Long id, @RequestBody MemberLevel level) {
        level.setId(id);
        memberLevelService.updateById(level);
        return level;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return memberLevelService.removeById(id);
    }
}