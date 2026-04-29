package com.example.examsystembackend.controller.edu;

import com.example.examsystembackend.common.Result;
import com.example.examsystembackend.entity.edu.Course;
import com.example.examsystembackend.entity.edu.Student;
import com.example.examsystembackend.entity.edu.Score;
import com.example.examsystembackend.service.edu.EducationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edu/education")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/courses")
    public Result<List<Course>> getCourses() {
        return Result.success(educationService.getCourses());
    }

    @PostMapping("/courses")
    public Result<Course> addCourse(@RequestBody Course course) {
        return Result.success(educationService.addCourse(course));
    }

    @PutMapping("/courses/{id}")
    public Result<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return Result.success(educationService.updateCourse(id, course));
    }

    @DeleteMapping("/courses/{id}")
    public Result<Void> deleteCourse(@PathVariable Long id) {
        educationService.deleteCourse(id);
        return Result.success();
    }

    @GetMapping("/students")
    public Result<List<Student>> getStudents(@RequestParam(required = false) String keyword) {
        return Result.success(educationService.getStudents(keyword));
    }

    @GetMapping("/students/{id}")
    public Result<Student> getStudentById(@PathVariable Long id) {
        return Result.success(educationService.getStudentById(id));
    }

    @PostMapping("/students")
    public Result<Student> addStudent(@RequestBody Student student) {
        return Result.success(educationService.addStudent(student));
    }

    @GetMapping("/scores")
    public Result<List<Score>> getScores(@RequestParam(required = false) Long courseId) {
        return Result.success(educationService.getScores(courseId));
    }

    @PostMapping("/scores")
    public Result<Score> addScore(@RequestBody Score score) {
        return Result.success(educationService.addScore(score));
    }

    @GetMapping("/scores/statistics")
    public Result<Object> getScoreStatistics(@RequestParam Long courseId) {
        return Result.success(educationService.getScoreStatistics(courseId));
    }
}