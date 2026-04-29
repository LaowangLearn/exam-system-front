package com.example.examsystembackend.controller.edu;

import com.example.examsystembackend.common.Result;
import com.example.examsystembackend.entity.edu.Message;
import com.example.examsystembackend.entity.edu.Homework;
import com.example.examsystembackend.entity.edu.ScoreNotice;
import com.example.examsystembackend.entity.edu.Notice;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/edu/home")
public class HomeController {

    @GetMapping("/messages")
    public Result<List<Message>> getMessages() {
        List<Message> messages = Arrays.asList(
            new Message(1L, "👨‍👩‍👧", "张三家长", "老师您好，请问明天的家长会几点开始？", "10:30", true),
            new Message(2L, "👨‍👩‍👧", "李四家长", "孩子最近学习情况怎么样？", "09:15", false),
            new Message(3L, "👨‍👩‍👧", "王五家长", "作业已经收到，谢谢老师！", "08:45", false),
            new Message(4L, "👨‍👩‍👧", "赵六家长", "请问周末有没有补课安排？", "昨天", false)
        );
        return Result.success(messages);
    }

    @PostMapping("/messages")
    public Result<Message> sendMessage(@RequestBody Message message) {
        return Result.success(new Message(
            System.currentTimeMillis(),
            "👨‍🏫",
            "老师",
            message.getContent(),
            "刚刚",
            false
        ));
    }

    @GetMapping("/homeworks")
    public Result<List<Homework>> getHomeworks() {
        List<Homework> homeworks = Arrays.asList(
            new Homework(1L, "数学", "完成练习册第35-40页", "李老师", "2025-01-15", "published"),
            new Homework(2L, "语文", "作文：我的寒假生活", "王老师", "2025-01-16", "published"),
            new Homework(3L, "英语", "背诵Unit5单词", "陈老师", "2025-01-15", "published"),
            new Homework(4L, "物理", "完成实验报告", "张老师", "2025-01-17", "draft")
        );
        return Result.success(homeworks);
    }

    @PostMapping("/homeworks")
    public Result<Homework> addHomework(@RequestBody Homework homework) {
        return Result.success(new Homework(
            System.currentTimeMillis(),
            homework.getSubject(),
            homework.getTitle(),
            "当前用户",
            homework.getDueDate(),
            "published"
        ));
    }

    @GetMapping("/scores")
    public Result<List<ScoreNotice>> getScores() {
        List<ScoreNotice> scores = Arrays.asList(
            new ScoreNotice(1L, "数学", 92, "期中考试"),
            new ScoreNotice(2L, "语文", 88, "期中考试"),
            new ScoreNotice(3L, "英语", 95, "期中考试"),
            new ScoreNotice(4L, "物理", 85, "期中考试")
        );
        return Result.success(scores);
    }

    @PostMapping("/scores")
    public Result<ScoreNotice> addScore(@RequestBody ScoreNotice score) {
        return Result.success(score);
    }

    @GetMapping("/notices")
    public Result<List<Notice>> getNotices() {
        List<Notice> notices = Arrays.asList(
            new Notice(1L, "关于寒假放假安排的通知", "2025-01-10", "info"),
            new Notice(2L, "本周六家长会通知", "2025-01-09", "important"),
            new Notice(3L, "冬季流感预防提醒", "2025-01-08", "warning"),
            new Notice(4L, "校园安全注意事项", "2025-01-05", "info")
        );
        return Result.success(notices);
    }

    @PostMapping("/notices")
    public Result<Notice> addNotice(@RequestBody Notice notice) {
        return Result.success(new Notice(
            System.currentTimeMillis(),
            notice.getTitle(),
            java.time.LocalDate.now().toString(),
            "info"
        ));
    }

    @GetMapping("/stats")
    public Result<Object> getStats() {
        return Result.success(java.util.Map.of(
            "parents", 3256,
            "messages", 234,
            "homework", 12,
            "notices", 5
        ));
    }
}