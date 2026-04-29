package com.example.examsystembackend.entity.edu;

public class Homework {
    private Long id;
    private String subject;
    private String title;
    private String teacher;
    private String dueDate;
    private String status;

    public Homework() {}

    public Homework(Long id, String subject, String title, String teacher, String dueDate, String status) {
        this.id = id;
        this.subject = subject;
        this.title = title;
        this.teacher = teacher;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}