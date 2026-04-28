package com.example.examsystembackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.MemberPointsRecord;
import com.example.examsystembackend.mapper.MemberPointsRecordMapper;
import com.example.examsystembackend.service.MemberPointsRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberPointsRecordServiceImpl extends ServiceImpl<MemberPointsRecordMapper, MemberPointsRecord> implements MemberPointsRecordService {

    @Override
    public List<MemberPointsRecord> getByMemberId(Long memberId) {
        return baseMapper.selectByMemberId(memberId);
    }
}