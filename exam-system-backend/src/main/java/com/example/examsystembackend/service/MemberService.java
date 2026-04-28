package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.Member;

import java.math.BigDecimal;
import java.util.List;

public interface MemberService extends IService<Member> {
    String generateMemberNo();
    Member getByPhone(String phone);
    List<Member> getByLevel(String level);
    void addPoints(Long memberId, BigDecimal points, String source, String remark);
    void deductPoints(Long memberId, BigDecimal points, String source, String remark);
    void updateLevel(Long memberId);
}