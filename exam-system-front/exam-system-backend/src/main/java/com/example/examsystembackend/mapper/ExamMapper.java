package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.Exam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考试数据访问层
 */
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
}