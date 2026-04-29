package com.example.examsystembackend.controller.edu;

import com.example.examsystembackend.common.Result;
import com.example.examsystembackend.entity.edu.Device;
import com.example.examsystembackend.entity.edu.Alarm;
import com.example.examsystembackend.entity.edu.EnergyData;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/edu/smart")
public class SmartController {

    @GetMapping("/devices")
    public Result<List<Device>> getDevices(@RequestParam(required = false) String type) {
        List<Device> devices = Arrays.asList(
            new Device(1L, "主入口摄像头", "南门入口", "camera", "online"),
            new Device(2L, "教学楼A栋门禁", "教学楼A栋", "access", "online"),
            new Device(3L, "图书馆传感器", "图书馆", "sensor", "online"),
            new Device(4L, "体育馆摄像头", "体育馆", "camera", "offline"),
            new Device(5L, "宿舍区门禁", "学生宿舍1号楼", "access", "online"),
            new Device(6L, "食堂传感器", "第一食堂", "sensor", "online")
        );
        if (type != null && !type.equals("all")) {
            devices = devices.stream().filter(d -> type.equals(d.getType())).toList();
        }
        return Result.success(devices);
    }

    @GetMapping("/devices/{id}")
    public Result<Device> getDeviceById(@PathVariable Long id) {
        Device device = new Device(id, "设备" + id, "未知位置", "camera", "online");
        return Result.success(device);
    }

    @PutMapping("/devices/{id}/status")
    public Result<Device> updateDeviceStatus(@PathVariable Long id, @RequestParam String status) {
        Device device = new Device(id, "设备" + id, "未知位置", "camera", status);
        return Result.success(device);
    }

    @GetMapping("/alarms")
    public Result<List<Alarm>> getAlarms() {
        List<Alarm> alarms = Arrays.asList(
            new Alarm(1L, "门禁异常开启", "10:35", "warning"),
            new Alarm(2L, "烟雾传感器触发", "09:20", "danger"),
            new Alarm(3L, "设备离线告警", "08:45", "info")
        );
        return Result.success(alarms);
    }

    @PostMapping("/alarms/{id}/handle")
    public Result<Void> handleAlarm(@PathVariable Long id) {
        return Result.success();
    }

    @GetMapping("/energy")
    public Result<List<EnergyData>> getEnergyData(@RequestParam String timeRange) {
        List<EnergyData> data = Arrays.asList(
            new EnergyData("00:00", 30.0),
            new EnergyData("04:00", 20.0),
            new EnergyData("08:00", 65.0),
            new EnergyData("12:00", 85.0),
            new EnergyData("16:00", 70.0),
            new EnergyData("20:00", 55.0),
            new EnergyData("24:00", 35.0)
        );
        return Result.success(data);
    }

    @GetMapping("/energy/statistics")
    public Result<Object> getEnergyStatistics() {
        return Result.success(java.util.Map.of(
            "peakPower", 234.5,
            "avgPower", 156.2,
            "todayUsage", 1256.8,
            "changeRate", -12.5
        ));
    }

    @GetMapping("/security/stats")
    public Result<Object> getSecurityStats() {
        return Result.success(java.util.Map.of(
            "alerts", 3,
            "devices", 56,
            "onlineRate", 98
        ));
    }
}