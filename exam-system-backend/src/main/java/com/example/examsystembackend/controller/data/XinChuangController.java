package com.example.examsystembackend.controller.data;

import com.example.examsystembackend.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data/xinchuang")
public class XinChuangController {

    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverview() {
        return Result.success(Map.of(
            "compatible", 15,
            "inProgress", 3,
            "pending", 2,
            "percentage", 83
        ));
    }

    @GetMapping("/os")
    public Result<List<Map<String, Object>>> getOSList() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "麒麟操作系统", "version", "V10", "icon", "🐧", "status", "supported", "statusText", "已兼容"),
            Map.of("id", 2, "name", "统信UOS", "version", "20", "icon", "🐧", "status", "supported", "statusText", "已兼容"),
            Map.of("id", 3, "name", "中科方德", "version", "V5.0", "icon", "🐧", "status", "in-progress", "statusText", "适配中"),
            Map.of("id", 4, "name", "深度Linux", "version", "20.5", "icon", "🐧", "status", "supported", "statusText", "已兼容")
        ));
    }

    @GetMapping("/database")
    public Result<List<Map<String, Object>>> getDatabaseList() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "达梦数据库", "desc", "DM8", "icon", "🗄️", "status", "supported"),
            Map.of("id", 2, "name", "人大金仓", "desc", "KingbaseES", "icon", "🗄️", "status", "supported"),
            Map.of("id", 3, "name", "华为高斯", "desc", "GaussDB", "icon", "🗄️", "status", "supported"),
            Map.of("id", 4, "name", "南大通用", "desc", "GBase", "icon", "🗄️", "status", "in-progress")
        ));
    }

    @GetMapping("/crypto")
    public Result<List<Map<String, Object>>> getCryptoList() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "SM2", "algo", "非对称加密算法", "icon", "🔐"),
            Map.of("id", 2, "name", "SM3", "algo", "哈希算法", "icon", "🔗"),
            Map.of("id", 3, "name", "SM4", "algo", "对称加密算法", "icon", "🔑"),
            Map.of("id", 4, "name", "SM9", "algo", "标识密码算法", "icon", "📛")
        ));
    }

    @GetMapping("/middleware")
    public Result<List<Map<String, Object>>> getMiddlewareList() {
        return Result.success(Arrays.asList(
            Map.of("id", 1, "name", "东方通TongWeb", "type", "应用服务器", "status", "supported", "statusText", "已兼容"),
            Map.of("id", 2, "name", "金蝶中间件", "type", "应用服务器", "status", "supported", "statusText", "已兼容"),
            Map.of("id", 3, "name", "中创中间件", "type", "应用服务器", "status", "in-progress", "statusText", "适配中"),
            Map.of("id", 4, "name", "宝兰德", "type", "应用服务器", "status", "supported", "statusText", "已兼容")
        ));
    }

    @PostMapping("/detect")
    public Result<Map<String, Object>> runDetection() {
        return Result.success(Map.of(
            "status", "pass",
            "message", "检测通过",
            "details", Arrays.asList(
                Map.of("label", "操作系统", "result", "pass", "message", "兼容"),
                Map.of("label", "数据库", "result", "pass", "message", "兼容"),
                Map.of("label", "中间件", "result", "pass", "message", "兼容"),
                Map.of("label", "国密算法", "result", "pass", "message", "已启用"),
                Map.of("label", "安全合规", "result", "pass", "message", "通过")
            )
        ));
    }

    @GetMapping("/certifications")
    public Result<List<Map<String, Object>>> getCertifications() {
        return Result.success(Arrays.asList(
            Map.of("name", "等保三级认证", "status", "certified"),
            Map.of("name", "信创产品认证", "status", "certified"),
            Map.of("name", "国密算法认证", "status", "certified")
        ));
    }
}