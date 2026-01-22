package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Exam;
import com.example.examsystembackend.entity.ExamQuestion;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.examsystembackend.service.ExamService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考试控制器
 */
@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    /**
     * 保存/暂存考试
     */
    @PostMapping
    public Exam saveExam(@RequestBody ExamSaveRequest request) {
        return examService.saveExamWithQuestions(request.getExam(), request.getQuestions());
    }

    /**
     * 提交考试
     */
    @PutMapping("/{id}/submit")
    public Map<String, Object> submitExam(@PathVariable Long id) {
        boolean success = examService.submitExam(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "提交成功" : "提交失败");
        return result;
    }

    /**
     * 根据考试ID查询详情（包含试题）
     */
    @GetMapping("/{id}")
    public ExamDetailResponse getExamDetail(@PathVariable Long id) {
        Exam exam = examService.getById(id);
        List<ExamQuestion> questions = examService.getQuestionsByExamId(id);
        return new ExamDetailResponse(exam, questions);
    }

    /**
     * 根据创建人查询考试列表
     */
    @GetMapping("/list/{user}")
    public List<Exam> getExamList(@PathVariable String user) {
        return examService.getExamListByUser(user);
    }

    /**
     * 保存考试请求参数
     */
    @Data
    public static class ExamSaveRequest {
        private Exam exam;
        private List<ExamQuestion> questions;
    }

    /**
     * 考试详情返回结果
     */
    @Data
    public static class ExamDetailResponse {
        private Exam exam;
        private List<ExamQuestion> questions;

        public ExamDetailResponse(Exam exam, List<ExamQuestion> questions) {
            this.exam = exam;
            this.questions = questions;
        }
    }
}