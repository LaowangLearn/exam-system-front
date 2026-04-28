package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    Member selectByPhone(@Param("phone") String phone);
    List<Member> selectByLevel(@Param("level") String level);
}