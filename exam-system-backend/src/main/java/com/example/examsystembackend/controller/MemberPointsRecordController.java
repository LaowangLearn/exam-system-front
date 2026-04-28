package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.MemberPointsRecord;
import com.example.examsystembackend.service.MemberPointsRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/points-records")
@RequiredArgsConstructor
public class MemberPointsRecordController {

    private final MemberPointsRecordService memberPointsRecordService;

    @GetMapping("/member/{memberId}")
    public List<MemberPointsRecord> getByMemberId(@PathVariable Long memberId) {
        return memberPointsRecordService.getByMemberId(memberId);
    }

    @GetMapping("/{id}")
    public MemberPointsRecord getById(@PathVariable Long id) {
        return memberPointsRecordService.getById(id);
    }
}