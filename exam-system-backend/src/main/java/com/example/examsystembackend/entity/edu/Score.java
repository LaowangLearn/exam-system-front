package com.example.examsystembackend.entity.edu;

public class Score {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer usualScore;
    private Integer finalScore;
    private Integer totalScore;
    private String examType;

    public Score() {}

    public Score(Long id, Long studentId, Long courseId, Integer usualScore, Integer finalScore, Integer totalScore, String examType) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.usualScore = usualScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
        this.examType = examType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Integer getUsualScore() { return usualScore; }
    public void setUsualScore(Integer usualScore) { this.usualScore = usualScore; }
    public Integer getFinalScore() { return finalScore; }
    public void setFinalScore(Integer finalScore) { this.finalScore = finalScore; }
    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
}