package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Member;
import com.example.examsystembackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public IPage<Member> list(@RequestParam(defaultValue = "1") int page, 
                              @RequestParam(defaultValue = "10") int size) {
        return memberService.page(new Page<>(page, size));
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @GetMapping("/phone/{phone}")
    public Member getByPhone(@PathVariable String phone) {
        return memberService.getByPhone(phone);
    }

    @GetMapping("/level/{level}")
    public List<Member> getByLevel(@PathVariable String level) {
        return memberService.getByLevel(level);
    }

    @PostMapping
    public Member create(@RequestBody Member member) {
        member.setMemberNo(memberService.generateMemberNo());
        member.setPoints(BigDecimal.ZERO);
        member.setTotalConsume(BigDecimal.ZERO);
        member.setOrderCount(0);
        member.setRegisterDate(LocalDate.now());
        member.setExpireDate(LocalDate.now().plusYears(1));
        member.setCreateTime(LocalDateTime.now());
        memberService.save(member);
        return member;
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody Member member) {
        member.setId(id);
        member.setUpdateTime(LocalDateTime.now());
        memberService.updateById(member);
        return member;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return memberService.removeById(id);
    }

    @PostMapping("/{id}/points/add")
    public Member addPoints(@PathVariable Long id, 
                           @RequestParam BigDecimal points,
                           @RequestParam String source,
                           @RequestParam(required = false) String remark) {
        memberService.addPoints(id, points, source, remark);
        return memberService.getById(id);
    }

    @PostMapping("/{id}/points/deduct")
    public Member deductPoints(@PathVariable Long id,
                              @RequestParam BigDecimal points,
                              @RequestParam String source,
                              @RequestParam(required = false) String remark) {
        memberService.deductPoints(id, points, source, remark);
        return memberService.getById(id);
    }

    @GetMapping("/statistics")
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", memberService.count());
        result.put("active", memberService.lambdaQuery().eq(Member::getStatus, "ACTIVE").count());
        return result;
    }
}