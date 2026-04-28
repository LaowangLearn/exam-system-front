package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.examsystembackend.entity.MemberPointsRecord;

import java.util.List;

public interface MemberPointsRecordService extends IService<MemberPointsRecord> {
    List<MemberPointsRecord> getByMemberId(Long memberId);
}