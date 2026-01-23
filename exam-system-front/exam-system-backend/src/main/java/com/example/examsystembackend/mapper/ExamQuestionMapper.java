package com.example.examsystembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.examsystembackend.entity.ExamQuestion;
import org.apache.ibatis.annotations.Mapper;

/**
 * 试题数据访问层
 */
@Mapper
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
}