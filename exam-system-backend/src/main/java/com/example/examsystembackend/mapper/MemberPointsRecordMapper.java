package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.MemberPointsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberPointsRecordMapper extends BaseMapper<MemberPointsRecord> {
    List<MemberPointsRecord> selectByMemberId(@Param("memberId") Long memberId);
}