package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.PerformanceEvaluation;
import com.example.examsystembackend.service.PerformanceEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm/performance")
@RequiredArgsConstructor
public class PerformanceEvaluationController {

    private final PerformanceEvaluationService performanceEvaluationService;

    @GetMapping
    public List<PerformanceEvaluation> getAllEvaluations() {
        return performanceEvaluationService.list();
    }

    @GetMapping("/{id}")
    public PerformanceEvaluation getEvaluationById(@PathVariable Long id) {
        return performanceEvaluationService.getById(id);
    }

    @PostMapping
    public boolean createEvaluation(@RequestBody PerformanceEvaluation evaluation) {
        return performanceEvaluationService.save(evaluation);
    }

    @PutMapping("/{id}")
    public boolean updateEvaluation(@PathVariable Long id, @RequestBody PerformanceEvaluation evaluation) {
        evaluation.setId(id);
        return performanceEvaluationService.updateById(evaluation);
    }

    @DeleteMapping("/{id}")
    public boolean deleteEvaluation(@PathVariable Long id) {
        return performanceEvaluationService.removeById(id);
    }
}
