package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.TrainingPlan;
import com.example.examsystembackend.service.TrainingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm/training")
@RequiredArgsConstructor
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    @GetMapping
    public List<TrainingPlan> getAllTrainingPlans() {
        return trainingPlanService.list();
    }

    @GetMapping("/{id}")
    public TrainingPlan getTrainingPlanById(@PathVariable Long id) {
        return trainingPlanService.getById(id);
    }

    @PostMapping
    public boolean createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        return trainingPlanService.save(trainingPlan);
    }

    @PutMapping("/{id}")
    public boolean updateTrainingPlan(@PathVariable Long id, @RequestBody TrainingPlan trainingPlan) {
        trainingPlan.setId(id);
        return trainingPlanService.updateById(trainingPlan);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTrainingPlan(@PathVariable Long id) {
        return trainingPlanService.removeById(id);
    }
}
