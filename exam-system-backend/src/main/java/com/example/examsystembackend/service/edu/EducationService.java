package com.example.examsystembackend.service.edu;

import com.example.examsystembackend.entity.edu.Course;
import com.example.examsystembackend.entity.edu.Student;
import com.example.examsystembackend.entity.edu.Score;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EducationService {

    public List<Course> getCourses() {
        return Arrays.asList(
            new Course(1L, "高等数学", "张教授", 4, 64, "2025-1", "active"),
            new Course(2L, "大学物理", "李教授", 4, 64, "2025-1", "active"),
            new Course(3L, "程序设计基础", "王老师", 3, 48, "2025-1", "active"),
            new Course(4L, "英语", "陈老师", 3, 48, "2025-1", "active"),
            new Course(5L, "线性代数", "赵教授", 3, 48, "2024-2", "ended"),
            new Course(6L, "数据结构", "刘老师", 4, 64, "2025-1", "active")
        );
    }

    public Course addCourse(Course course) {
        return new Course(System.currentTimeMillis(), course.getName(), course.getTeacher(),
            course.getCredits(), course.getHours(), course.getSemester(), "active");
    }

    public Course updateCourse(Long id, Course course) {
        return new Course(id, course.getName(), course.getTeacher(),
            course.getCredits(), course.getHours(), course.getSemester(), course.getStatus());
    }

    public void deleteCourse(Long id) {
    }

    public List<Student> getStudents(String keyword) {
        List<Student> students = Arrays.asList(
            new Student(1L, "2021001", "张三", "male", "2021级", "计算机科学", "计科2101", "active"),
            new Student(2L, "2021002", "李四", "female", "2021级", "计算机科学", "计科2101", "active"),
            new Student(3L, "2021003", "王五", "male", "2021级", "软件工程", "软工2102", "active"),
            new Student(4L, "2022001", "赵六", "female", "2022级", "人工智能", "AI2201", "active"),
            new Student(5L, "2022002", "钱七", "male", "2022级", "数据科学", "数科2201", "graduated"),
            new Student(6L, "2023001", "孙八", "female", "2023级", "计算机科学", "计科2301", "active")
        );
        
        if (keyword != null && !keyword.isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(lowerKeyword) || s.getStudentNo().contains(keyword))
                .collect(Collectors.toList());
        }
        return students;
    }

    public Student getStudentById(Long id) {
        return new Student(id, "2021001", "张三", "male", "2021级", "计算机科学", "计科2101", "active");
    }

    public Student addStudent(Student student) {
        return new Student(System.currentTimeMillis(), student.getStudentNo(), student.getName(),
            student.getGender(), student.getGrade(), student.getMajor(), student.getClazz(), "active");
    }

    public List<Score> getScores(Long courseId) {
        return Arrays.asList(
            new Score(1L, 1L, courseId != null ? courseId : 1L, 90, 85, 87, "期中考试"),
            new Score(2L, 2L, courseId != null ? courseId : 1L, 85, 90, 88, "期中考试"),
            new Score(3L, 3L, courseId != null ? courseId : 1L, 80, 82, 81, "期中考试"),
            new Score(4L, 4L, courseId != null ? courseId : 1L, 95, 92, 93, "期中考试"),
            new Score(5L, 5L, courseId != null ? courseId : 1L, 75, 78, 77, "期中考试"),
            new Score(6L, 6L, courseId != null ? courseId : 1L, 88, 86, 87, "期中考试")
        );
    }

    public Score addScore(Score score) {
        return new Score(System.currentTimeMillis(), score.getStudentId(), score.getCourseId(),
            score.getUsualScore(), score.getFinalScore(), score.getTotalScore(), score.getExamType());
    }

    public Object getScoreStatistics(Long courseId) {
        return java.util.Map.of(
            "avgScore", 86.5,
            "maxScore", 95,
            "minScore", 75,
            "excellentCount", 2,
            "goodCount", 3,
            "passCount", 1
        );
    }
}