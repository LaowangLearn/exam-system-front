package com.example.examsystembackend.controller.data;

import com.example.examsystembackend.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data/ai")
public class AIAgentController {

    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        return Result.success(Map.of(
            "id", System.currentTimeMillis(),
            "type", "bot",
            "content", "好的，我已经收到您的请求：\"" + message + "\"\n\n根据您的需求，我将为您提供专业的解决方案。这是一个详细的回答，包含多个要点：\n\n1. 首先分析问题的核心要点\n2. 提供可行的解决方案\n3. 给出具体的实施步骤\n\n如果您需要更详细的信息，请随时告诉我！",
            "thought", "已调用文档处理工具进行分析"
        ));
    }

    @GetMapping("/tools")
    public Result<List<Map<String, Object>>> getTools() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "文档处理", "desc", "文档生成、摘要、翻译", "icon", "📄", "active", true),
            Map.of("id", 2, "name", "数据查询", "desc", "数据库查询、数据分析", "icon", "📊", "active", true),
            Map.of("id", 3, "name", "日程管理", "desc", "日历查询、会议安排", "icon", "📅", "active", false),
            Map.of("id", 4, "name", "邮件发送", "desc", "自动发送邮件", "icon", "📧", "active", true),
            Map.of("id", 5, "name", "网页搜索", "desc", "实时搜索获取信息", "icon", "🔍", "active", false),
            Map.of("id", 6, "name", "代码生成", "desc", "生成代码片段", "icon", "💻", "active", true)
        ));
    }

    @PutMapping("/tools/{id}/toggle")
    public Result<Map<String, Object>> toggleTool(@PathVariable Long id) {
        return Result.success(Map.of("id", id, "active", true));
    }

    @GetMapping("/tasks")
    public Result<List<Map<String, Object>>> getTasks() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "title", "周报生成任务", "status", "执行中", "progress", "65%", "percent", 65),
            Map.of("id", 2, "title", "文档翻译", "status", "等待中", "progress", "0%", "percent", 0),
            Map.of("id", 3, "title", "数据分析报告", "status", "已完成", "progress", "100%", "percent", 100)
        ));
    }

    @GetMapping("/quick-actions")
    public Result<List<Map<String, Object>>> getQuickActions() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "icon", "📊", "text", "生成报表"),
            Map.of("id", 2, "icon", "📝", "text", "撰写文档"),
            Map.of("id", 3, "icon", "📅", "text", "安排日程"),
            Map.of("id", 4, "icon", "📧", "text", "发送邮件"),
            Map.of("id", 5, "icon", "🔍", "text", "搜索资料"),
            Map.of("id", 6, "icon", "🤔", "text", "分析数据")
        ));
    }
}