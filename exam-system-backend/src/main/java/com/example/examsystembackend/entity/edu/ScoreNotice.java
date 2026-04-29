package com.example.examsystembackend.entity.edu;

public class ScoreNotice {
    private Long id;
    private String subject;
    private Integer score;
    private String exam;

    public ScoreNotice() {}

    public ScoreNotice(Long id, String subject, Integer score, String exam) {
        this.id = id;
        this.subject = subject;
        this.score = score;
        this.exam = exam;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getExam() { return exam; }
    public void setExam(String exam) { this.exam = exam; }
}