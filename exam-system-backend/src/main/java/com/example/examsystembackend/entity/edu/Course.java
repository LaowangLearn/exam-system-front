package com.example.examsystembackend.entity.edu;

public class Course {
    private Long id;
    private String name;
    private String teacher;
    private Integer credits;
    private Integer hours;
    private String semester;
    private String status;

    public Course() {}

    public Course(Long id, String name, String teacher, Integer credits, Integer hours, String semester, String status) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
        this.hours = hours;
        this.semester = semester;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}