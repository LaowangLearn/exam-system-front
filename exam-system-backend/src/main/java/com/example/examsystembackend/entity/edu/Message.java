package com.example.examsystembackend.entity.edu;

public class Message {
    private Long id;
    private String avatar;
    private String name;
    private String content;
    private String time;
    private Boolean unread;

    public Message() {}

    public Message(Long id, String avatar, String name, String content, String time, Boolean unread) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.content = content;
        this.time = time;
        this.unread = unread;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public Boolean getUnread() { return unread; }
    public void setUnread(Boolean unread) { this.unread = unread; }
}