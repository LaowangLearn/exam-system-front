package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Member;
import com.example.examsystembackend.entity.MemberLevel;
import com.example.examsystembackend.entity.MemberPointsRecord;
import com.example.examsystembackend.mapper.MemberMapper;
import com.example.examsystembackend.service.MemberLevelService;
import com.example.examsystembackend.service.MemberPointsRecordService;
import com.example.examsystembackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    private final MemberLevelService memberLevelService;
    private final MemberPointsRecordService memberPointsRecordService;

    @Override
    public String generateMemberNo() {
        String prefix = "MEM";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = this.count();
        return prefix + date + String.format("%04d", count + 1);
    }

    @Override
    public Member getByPhone(String phone) {
        return baseMapper.selectByPhone(phone);
    }

    @Override
    public List<Member> getByLevel(String level) {
        return baseMapper.selectByLevel(level);
    }

    @Override
    @Transactional
    public void addPoints(Long memberId, BigDecimal points, String source, String remark) {
        Member member = this.getById(memberId);
        if (member != null) {
            BigDecimal newBalance = member.getPoints().add(points);
            member.setPoints(newBalance);
            this.updateById(member);

            MemberPointsRecord record = new MemberPointsRecord();
            record.setMemberId(memberId);
            record.setType("ADD");
            record.setPoints(points);
            record.setBalance(newBalance);
            record.setSource(source);
            record.setRemark(remark);
            record.setCreateTime(LocalDateTime.now());
            memberPointsRecordService.save(record);

            updateLevel(memberId);
        }
    }

    @Override
    @Transactional
    public void deductPoints(Long memberId, BigDecimal points, String source, String remark) {
        Member member = this.getById(memberId);
        if (member != null && member.getPoints().compareTo(points) >= 0) {
            BigDecimal newBalance = member.getPoints().subtract(points);
            member.setPoints(newBalance);
            this.updateById(member);

            MemberPointsRecord record = new MemberPointsRecord();
            record.setMemberId(memberId);
            record.setType("DEDUCT");
            record.setPoints(points.negate());
            record.setBalance(newBalance);
            record.setSource(source);
            record.setRemark(remark);
            record.setCreateTime(LocalDateTime.now());
            memberPointsRecordService.save(record);
        }
    }

    @Override
    public void updateLevel(Long memberId) {
        Member member = this.getById(memberId);
        if (member != null) {
            MemberLevel level = memberLevelService.getLevelByPoints(member.getPoints());
            if (level != null && !level.getLevelCode().equals(member.getLevel())) {
                member.setLevel(level.getLevelCode());
                this.updateById(member);
            }
        }
    }
}