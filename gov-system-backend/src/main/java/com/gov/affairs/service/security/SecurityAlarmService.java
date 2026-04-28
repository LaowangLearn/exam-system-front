package com.gov.affairs.service.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.security.SecurityAlarm;
import com.gov.affairs.mapper.security.SecurityAlarmMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SecurityAlarmService extends ServiceImpl<SecurityAlarmMapper, SecurityAlarm> {

    public PageResult<SecurityAlarm> pageQuery(Integer pageNum, Integer pageSize, String alarmTitle, String alarmType,
                                                 String alarmLevel, String currentStatus, String startDate, String endDate) {
        Page<SecurityAlarm> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(alarmTitle)) {
            wrapper.like(SecurityAlarm::getAlarmTitle, alarmTitle);
        }
        if (StringUtils.hasText(alarmType)) {
            wrapper.eq(SecurityAlarm::getAlarmType, alarmType);
        }
        if (StringUtils.hasText(alarmLevel)) {
            wrapper.eq(SecurityAlarm::getAlarmLevel, alarmLevel);
        }
        if (StringUtils.hasText(currentStatus)) {
            wrapper.eq(SecurityAlarm::getCurrentStatus, currentStatus);
        }
        if (StringUtils.hasText(startDate)) {
            wrapper.ge(SecurityAlarm::getAlarmTime, startDate);
        }
        if (StringUtils.hasText(endDate)) {
            wrapper.le(SecurityAlarm::getAlarmTime, endDate);
        }
        wrapper.orderByDesc(SecurityAlarm::getAlarmTime);
        Page<SecurityAlarm> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public boolean dispatchAlarm(Long alarmId, Long handleDepartmentId, String handleDepartmentName,
                                Long handleUserId, String handleUserName) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setHandleDepartmentId(handleDepartmentId);
        alarm.setHandleDepartmentName(handleDepartmentName);
        alarm.setHandleUserId(handleUserId);
        alarm.setHandleUserName(handleUserName);
        alarm.setCurrentStatus("DISPATCHED");
        alarm.setDispatchTime(LocalDateTime.now());
        alarm.setDispatchUserId(handleUserId);
        alarm.setDispatchUserName(handleUserName);
        return this.updateById(alarm);
    }

    public boolean arriveAlarm(Long alarmId) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setCurrentStatus("ARRIVED");
        alarm.setArriveTime(LocalDateTime.now());
        return this.updateById(alarm);
    }

    public boolean startHandleAlarm(Long alarmId) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setCurrentStatus("HANDLING");
        alarm.setHandleStartTime(LocalDateTime.now());
        return this.updateById(alarm);
    }

    public boolean resolveAlarm(Long alarmId, String handleResult, String handleDetail, String handleMeaures) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setCurrentStatus("RESOLVED");
        alarm.setHandleEndTime(LocalDateTime.now());
        alarm.setHandleResult(handleResult);
        alarm.setHandleDetail(handleDetail);
        alarm.setHandleMeaures(handleMeaures);

        if (alarm.getDispatchTime() != null) {
            long responseMs = java.time.Duration.between(alarm.getDispatchTime(), LocalDateTime.now()).toMillis();
            alarm.setResponseTimeMinutes((int) (responseMs / 60000));
        }
        if (alarm.getHandleStartTime() != null) {
            long resolveMs = java.time.Duration.between(alarm.getHandleStartTime(), LocalDateTime.now()).toMillis();
            alarm.setResolveTimeMinutes((int) (resolveMs / 60000));
        }

        return this.updateById(alarm);
    }

    public boolean markFalseAlarm(Long alarmId, String feedbackContent) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setCurrentStatus("FALSE_ALARM");
        alarm.setHandleResult("FALSE");
        alarm.setFeedbackContent(feedbackContent);
        alarm.setHandleEndTime(LocalDateTime.now());
        return this.updateById(alarm);
    }

    public boolean closeAlarm(Long alarmId) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setCurrentStatus("CLOSED");
        return this.updateById(alarm);
    }

    public boolean evaluateAlarm(Long alarmId, Integer score, String content) {
        SecurityAlarm alarm = this.getById(alarmId);
        if (alarm == null) return false;
        alarm.setEvaluateScore(score);
        alarm.setEvaluateContent(content);
        return this.updateById(alarm);
    }

    public Map<String, Object> getStatistics() {
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityAlarm::getStatus, 1);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", this.count(wrapper));

        LambdaQueryWrapper<SecurityAlarm> pendingWrapper = new LambdaQueryWrapper<>(wrapper);
        pendingWrapper.eq(SecurityAlarm::getCurrentStatus, "PENDING");
        stats.put("pending", this.count(pendingWrapper));

        LambdaQueryWrapper<SecurityAlarm> handlingWrapper = new LambdaQueryWrapper<>(wrapper);
        handlingWrapper.eq(SecurityAlarm::getCurrentStatus, "DISPATCHED");
        handlingWrapper.or().eq(SecurityAlarm::getCurrentStatus, "ARRIVED");
        handlingWrapper.or().eq(SecurityAlarm::getCurrentStatus, "HANDLING");
        stats.put("handling", this.count(handlingWrapper));

        LambdaQueryWrapper<SecurityAlarm> resolvedWrapper = new LambdaQueryWrapper<>(wrapper);
        resolvedWrapper.eq(SecurityAlarm::getCurrentStatus, "RESOLVED");
        stats.put("resolved", this.count(resolvedWrapper));

        LambdaQueryWrapper<SecurityAlarm> criticalWrapper = new LambdaQueryWrapper<>(wrapper);
        criticalWrapper.eq(SecurityAlarm::getAlarmLevel, "CRITICAL");
        criticalWrapper.eq(SecurityAlarm::getCurrentStatus, "PENDING");
        stats.put("criticalPending", this.count(criticalWrapper));

        return stats;
    }

    public List<SecurityAlarm> getPendingAlarms() {
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityAlarm::getCurrentStatus, "PENDING");
        wrapper.orderByDesc(SecurityAlarm::getAlarmLevel);
        wrapper.orderByAsc(SecurityAlarm::getAlarmTime);
        return this.list(wrapper);
    }

    public List<SecurityAlarm> getByDeviceId(Long deviceId) {
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityAlarm::getDeviceId, deviceId);
        wrapper.orderByDesc(SecurityAlarm::getAlarmTime);
        return this.list(wrapper);
    }

    public List<SecurityAlarm> getByGridCellId(Long gridCellId) {
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityAlarm::getGridCellId, gridCellId);
        wrapper.orderByDesc(SecurityAlarm::getAlarmTime);
        return this.list(wrapper);
    }

    public List<SecurityAlarm> getRealTimeAlarms() {
        LambdaQueryWrapper<SecurityAlarm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecurityAlarm::getCurrentStatus, "PENDING");
        wrapper.apply("alarm_time > {0}", LocalDateTime.now().minusHours(1));
        return this.list(wrapper);
    }
}
