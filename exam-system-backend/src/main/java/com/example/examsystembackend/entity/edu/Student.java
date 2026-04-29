package com.example.examsystembackend.entity.edu;

public class Student {
    private Long id;
    private String studentNo;
    private String name;
    private String gender;
    private String grade;
    private String major;
    private String clazz;
    private String status;

    public Student() {}

    public Student(Long id, String studentNo, String name, String gender, String grade, String major, String clazz, String status) {
        this.id = id;
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
        this.major = major;
        this.clazz = clazz;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getClazz() { return clazz; }
    public void setClazz(String clazz) { this.clazz = clazz; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}