package com.example.examsystembackend.controller.data;

import com.example.examsystembackend.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data/lowcode")
public class LowCodeController {

    @GetMapping("/components")
    public Result<List<Map<String, Object>>> getComponents() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "文本", "icon", "📝"),
            Map.of("id", 2, "name", "输入框", "icon", "📦"),
            Map.of("id", 3, "name", "按钮", "icon", "🔘"),
            Map.of("id", 4, "name", "表格", "icon", "📊"),
            Map.of("id", 5, "name", "表单", "icon", "📋"),
            Map.of("id", 6, "name", "图表", "icon", "📈"),
            Map.of("id", 7, "name", "图片", "icon", "🖼️"),
            Map.of("id", 8, "name", "导航", "icon", "🧭"),
            Map.of("id", 9, "name", "卡片", "icon", "🃏"),
            Map.of("id", 10, "name", "容器", "icon", "📦")
        ));
    }

    @GetMapping("/templates")
    public Result<List<Map<String, Object>>> getTemplates() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "登录页面", "type", "页面", "preview", "🔐"),
            Map.of("id", 2, "name", "数据表格", "type", "组件", "preview", "📊"),
            Map.of("id", 3, "name", "表单页面", "type", "页面", "preview", "📝"),
            Map.of("id", 4, "name", "仪表盘", "type", "页面", "preview", "📈")
        ));
    }

    @PostMapping("/project")
    public Result<Map<String, Object>> createProject(@RequestBody Map<String, Object> project) {
        return Result.success(Map.of(
            "id", System.currentTimeMillis(),
            "name", project.get("name"),
            "status", "created"
        ));
    }

    @PostMapping("/page")
    public Result<Map<String, Object>> createPage(@RequestBody Map<String, Object> page) {
        return Result.success(Map.of(
            "id", System.currentTimeMillis(),
            "name", page.get("name"),
            "status", "created"
        ));
    }

    @PostMapping("/component")
    public Result<Map<String, Object>> addComponent(@RequestBody Map<String, Object> component) {
        return Result.success(Map.of(
            "id", System.currentTimeMillis(),
            "type", component.get("type"),
            "status", "added"
        ));
    }

    @PutMapping("/component/{id}")
    public Result<Map<String, Object>> updateComponent(@PathVariable Long id, @RequestBody Map<String, Object> props) {
        return Result.success(Map.of("id", id, "status", "updated"));
    }

    @DeleteMapping("/component/{id}")
    public Result<Void> deleteComponent(@PathVariable Long id) {
        return Result.success();
    }

    @PostMapping("/preview")
    public Result<Map<String, Object>> preview(@RequestBody Map<String, Object> data) {
        return Result.success(Map.of("status", "success", "previewUrl", "/preview/" + System.currentTimeMillis()));
    }

    @PostMapping("/publish")
    public Result<Map<String, Object>> publish(@RequestBody Map<String, Object> project) {
        return Result.success(Map.of("status", "published", "url", "/app/" + project.get("name")));
    }
}