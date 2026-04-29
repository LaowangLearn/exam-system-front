package com.example.examsystembackend.entity.edu;

public class Notice {
    private Long id;
    private String title;
    private String time;
    private String type;

    public Notice() {}

    public Notice(Long id, String title, String time, String type) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}