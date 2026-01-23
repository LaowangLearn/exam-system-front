package com.example.examsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.entity.Exam;
import com.example.examsystembackend.entity.ExamQuestion;
import com.example.examsystembackend.enums.ExamStatus;
import com.example.examsystembackend.mapper.ExamMapper;
import com.example.examsystembackend.mapper.ExamQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试业务逻辑层
 */
@Service
@RequiredArgsConstructor
public class ExamService extends ServiceImpl<ExamMapper, Exam> {

    private final ExamQuestionMapper examQuestionMapper;

    /**
     * 保存/暂存考试（包含试题）
     */
    @Transactional
    public Exam saveExamWithQuestions(Exam exam, List<ExamQuestion> questions) {
        // 补全考试信息
        if (exam.getId() == null) {
            exam.setCreateTime(LocalDateTime.now());
            exam.setStatus(ExamStatus.DRAFT);
            this.save(exam);
        } else {
            exam.setUpdateTime(LocalDateTime.now());
            this.updateById(exam);
        }

        // 保存试题（先删除原有试题，再新增）
        QueryWrapper<ExamQuestion> questionWrapper = new QueryWrapper<>();
        questionWrapper.eq("exam_id", exam.getId());
        examQuestionMapper.delete(questionWrapper);

        for (ExamQuestion question : questions) {
            question.setExamId(exam.getId());
            examQuestionMapper.insert(question);
        }

        return exam;
    }

    /**
     * 提交考试（修改状态为已提交，不可再编辑）
     */
    @Transactional
    public boolean submitExam(Long examId) {
        Exam exam = this.getById(examId);
        if (exam == null) {
            return false;
        }
        // 仅草稿状态可提交
        if (exam.getStatus() == ExamStatus.DRAFT) {
            exam.setStatus(ExamStatus.COMMITTED);
            exam.setUpdateTime(LocalDateTime.now());
            return this.updateById(exam);
        }
        return false;
    }

    /**
     * 根据考试ID查询试题列表
     */
    public List<ExamQuestion> getQuestionsByExamId(Long examId) {
        QueryWrapper<ExamQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id", examId);
        return examQuestionMapper.selectList(wrapper);
    }

    /**
     * 根据创建人查询考试列表
     */
    public List<Exam> getExamListByUser(String createUser) {
        QueryWrapper<Exam> wrapper = new QueryWrapper<>();
        wrapper.eq("create_user", createUser)
                .orderByDesc("update_time");
        return this.list(wrapper);
    }
}