package com.example.examsystembackend.entity.edu;

public class Alarm {
    private Long id;
    private String title;
    private String time;
    private String level;

    public Alarm() {}

    public Alarm(Long id, String title, String time, String level) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.level = level;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}