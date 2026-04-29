package com.example.examsystembackend.controller.data;

import com.example.examsystembackend.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/data/bigdata")
public class BigDataController {

    @GetMapping("/stats")
    public Result<Object> getStats() {
        return Result.success(java.util.Map.of(
            "totalData", "100TB",
            "realtimeSpeed", "100K/s",
            "tasks", 128,
            "successRate", 99.5
        ));
    }

    @GetMapping("/pipelines")
    public Result<List<Object>> getPipelines() {
        return Result.success(Arrays.asList(
            java.util.Map.of("id", 1, "name", "用户行为数据", "source", "Kafka", "target", "HDFS", "status", "running"),
            java.util.Map.of("id", 2, "name", "订单数据同步", "source", "MySQL", "target", "Hive", "status", "success"),
            java.util.Map.of("id", 3, "name", "日志采集", "source", "Filebeat", "target", "ES", "status", "running"),
            java.util.Map.of("id", 4, "name", "数据清洗任务", "source", "HDFS", "target", "Hive", "status", "failed")
        ));
    }

    @GetMapping("/realtime")
    public Result<List<Double>> getRealtimeData() {
        return Result.success(Arrays.asList(30.0, 45.0, 60.0, 80.0, 95.0, 70.0, 55.0, 40.0));
    }

    @GetMapping("/tasks")
    public Result<List<Object>> getTasks() {
        return Result.success(Arrays.asList(
            java.util.Map.of("id", 1, "name", "每日数据汇总", "priority", "high", "progress", 75),
            java.util.Map.of("id", 2, "name", "用户画像更新", "priority", "medium", "progress", 45),
            java.util.Map.of("id", 3, "name", "报表生成", "priority", "low", "progress", 90)
        ));
    }

    @GetMapping("/quality")
    public Result<List<Object>> getQualityMetrics() {
        return Result.success(Arrays.asList(
            java.util.Map.of("name", "数据完整性", "value", "99.8%", "status", "good"),
            java.util.Map.of("name", "数据准确性", "value", "99.5%", "status", "good"),
            java.util.Map.of("name", "数据一致性", "value", "98.9%", "status", "medium"),
            java.util.Map.of("name", "数据时效性", "value", "99.2%", "status", "good")
        ));
    }

    @PostMapping("/pipeline")
    public Result<Object> createPipeline(@RequestBody Object pipeline) {
        return Result.success(java.util.Map.of("id", System.currentTimeMillis(), "status", "created"));
    }
}