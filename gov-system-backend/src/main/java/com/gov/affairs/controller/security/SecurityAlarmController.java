package com.gov.affairs.controller.security;

import com.gov.affairs.common.R;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.security.SecurityAlarm;
import com.gov.affairs.service.security.SecurityAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/security/alarm")
public class SecurityAlarmController {

    @Autowired
    private SecurityAlarmService securityAlarmService;

    @GetMapping("/page")
    public R<PageResult<SecurityAlarm>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String alarmTitle,
            @RequestParam(required = false) String alarmType,
            @RequestParam(required = false) String alarmLevel,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(securityAlarmService.pageQuery(pageNum, pageSize, alarmTitle, alarmType, alarmLevel, currentStatus, startDate, endDate));
    }

    @GetMapping("/{alarmId}")
    public R<SecurityAlarm> getById(@PathVariable Long alarmId) {
        return R.ok(securityAlarmService.getById(alarmId));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(securityAlarmService.getStatistics());
    }

    @GetMapping("/pending")
    public R<List<SecurityAlarm>> getPendingAlarms() {
        return R.ok(securityAlarmService.getPendingAlarms());
    }

    @GetMapping("/device/{deviceId}")
    public R<List<SecurityAlarm>> getByDeviceId(@PathVariable Long deviceId) {
        return R.ok(securityAlarmService.getByDeviceId(deviceId));
    }

    @GetMapping("/grid/{gridCellId}")
    public R<List<SecurityAlarm>> getByGridCellId(@PathVariable Long gridCellId) {
        return R.ok(securityAlarmService.getByGridCellId(gridCellId));
    }

    @GetMapping("/realtime")
    public R<List<SecurityAlarm>> getRealTimeAlarms() {
        return R.ok(securityAlarmService.getRealTimeAlarms());
    }

    @PostMapping
    public R<Boolean> create(@RequestBody SecurityAlarm alarm) {
        alarm.setCurrentStatus("PENDING");
        return R.ok(securityAlarmService.save(alarm));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody SecurityAlarm alarm) {
        return R.ok(securityAlarmService.updateById(alarm));
    }

    @DeleteMapping("/{alarmId}")
    public R<Boolean> delete(@PathVariable Long alarmId) {
        return R.ok(securityAlarmService.removeById(alarmId));
    }

    @PutMapping("/dispatch/{alarmId}")
    public R<Boolean> dispatchAlarm(@PathVariable Long alarmId,
                                    @RequestParam Long handleDepartmentId,
                                    @RequestParam String handleDepartmentName,
                                    @RequestParam(required = false) Long handleUserId,
                                    @RequestParam(required = false) String handleUserName) {
        return R.ok(securityAlarmService.dispatchAlarm(alarmId, handleDepartmentId, handleDepartmentName, handleUserId, handleUserName));
    }

    @PutMapping("/arrive/{alarmId}")
    public R<Boolean> arriveAlarm(@PathVariable Long alarmId) {
        return R.ok(securityAlarmService.arriveAlarm(alarmId));
    }

    @PutMapping("/start/{alarmId}")
    public R<Boolean> startHandleAlarm(@PathVariable Long alarmId) {
        return R.ok(securityAlarmService.startHandleAlarm(alarmId));
    }

    @PutMapping("/resolve/{alarmId}")
    public R<Boolean> resolveAlarm(@PathVariable Long alarmId,
                                  @RequestParam String handleResult,
                                  @RequestParam(required = false) String handleDetail,
                                  @RequestParam(required = false) String handleMeaures) {
        return R.ok(securityAlarmService.resolveAlarm(alarmId, handleResult, handleDetail, handleMeaures));
    }

    @PutMapping("/falsealarm/{alarmId}")
    public R<Boolean> markFalseAlarm(@PathVariable Long alarmId,
                                     @RequestParam(required = false) String feedbackContent) {
        return R.ok(securityAlarmService.markFalseAlarm(alarmId, feedbackContent));
    }

    @PutMapping("/close/{alarmId}")
    public R<Boolean> closeAlarm(@PathVariable Long alarmId) {
        return R.ok(securityAlarmService.closeAlarm(alarmId));
    }

    @PutMapping("/evaluate/{alarmId}")
    public R<Boolean> evaluateAlarm(@PathVariable Long alarmId,
                                   @RequestParam Integer score,
                                   @RequestParam(required = false) String content) {
        return R.ok(securityAlarmService.evaluateAlarm(alarmId, score, content));
    }
}
