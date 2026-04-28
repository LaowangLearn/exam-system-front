package com.example.examsystembackend.gov.controller.security;

import com.example.examsystembackend.gov.common.R;
import com.example.examsystembackend.gov.common.PageResult;
import com.example.examsystembackend.gov.entity.security.SecurityDevice;
import com.example.examsystembackend.gov.service.security.SecurityDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/security/device")
public class SecurityDeviceController {

    @Autowired
    private SecurityDeviceService securityDeviceService;

    @GetMapping("/page")
    public R<PageResult<SecurityDevice>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String deviceName,
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String deviceStatus,
            @RequestParam(required = false) String gridCellId) {
        return R.ok(securityDeviceService.pageQuery(pageNum, pageSize, deviceName, deviceType, deviceStatus, gridCellId));
    }

    @GetMapping("/{deviceId}")
    public R<SecurityDevice> getById(@PathVariable Long deviceId) {
        return R.ok(securityDeviceService.getById(deviceId));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(securityDeviceService.getStatistics());
    }

    @GetMapping("/grid/{gridCellId}")
    public R<List<SecurityDevice>> getByGridCellId(@PathVariable Long gridCellId) {
        return R.ok(securityDeviceService.getByGridCellId(gridCellId));
    }

    @GetMapping("/type/{deviceType}")
    public R<List<SecurityDevice>> getByDeviceType(@PathVariable String deviceType) {
        return R.ok(securityDeviceService.getByDeviceType(deviceType));
    }

    @GetMapping("/offline")
    public R<List<SecurityDevice>> getOfflineDevices() {
        return R.ok(securityDeviceService.getOfflineDevices());
    }

    @GetMapping("/maintenance/due")
    public R<List<SecurityDevice>> getMaintenanceDue() {
        return R.ok(securityDeviceService.getMaintenanceDue());
    }

    @PostMapping
    public R<Boolean> create(@RequestBody SecurityDevice device) {
        device.setDeviceStatus("OFFLINE");
        return R.ok(securityDeviceService.save(device));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody SecurityDevice device) {
        return R.ok(securityDeviceService.updateById(device));
    }

    @DeleteMapping("/{deviceId}")
    public R<Boolean> delete(@PathVariable Long deviceId) {
        return R.ok(securityDeviceService.removeById(deviceId));
    }

    @PutMapping("/status/{deviceId}")
    public R<Boolean> updateDeviceStatus(@PathVariable Long deviceId, @RequestParam String status) {
        return R.ok(securityDeviceService.updateDeviceStatus(deviceId, status));
    }

    @PostMapping("/heartbeat/{deviceId}")
    public R<Boolean> heartbeat(@PathVariable Long deviceId) {
        return R.ok(securityDeviceService.heartbeat(deviceId));
    }
}
