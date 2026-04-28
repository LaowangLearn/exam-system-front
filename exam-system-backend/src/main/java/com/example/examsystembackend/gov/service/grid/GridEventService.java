package com.example.examsystembackend.gov.service.grid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.examsystembackend.gov.common.PageResult;
import com.example.examsystembackend.gov.entity.grid.GridEvent;
import com.example.examsystembackend.gov.mapper.grid.GridEventMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GridEventService extends ServiceImpl<GridEventMapper, GridEvent> {

    public PageResult<GridEvent> pageQuery(Integer pageNum, Integer pageSize, String eventTitle, String eventType,
                                            String currentStatus, String urgentLevel, String startDate, String endDate) {
        Page<GridEvent> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<GridEvent> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(eventTitle)) {
            wrapper.like(GridEvent::getEventTitle, eventTitle);
        }
        if (StringUtils.hasText(eventType)) {
            wrapper.eq(GridEvent::getEventType, eventType);
        }
        if (StringUtils.hasText(currentStatus)) {
            wrapper.eq(GridEvent::getCurrentStatus, currentStatus);
        }
        if (StringUtils.hasText(urgentLevel)) {
            wrapper.eq(GridEvent::getUrgentLevel, urgentLevel);
        }
        if (StringUtils.hasText(startDate)) {
            wrapper.ge(GridEvent::getReportTime, startDate);
        }
        if (StringUtils.hasText(endDate)) {
            wrapper.le(GridEvent::getReportTime, endDate);
        }
        wrapper.orderByDesc(GridEvent::getReportTime);
        Page<GridEvent> result = this.page(page, wrapper);
        return PageResult.of(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }

    public boolean assignEvent(Long eventId, Long handleDepartmentId, String handleDepartmentName, String aiSuggestion) {
        GridEvent event = this.getById(eventId);
        if (event == null) return false;
        event.setHandleDepartmentId(handleDepartmentId);
        event.setHandleDepartmentName(handleDepartmentName);
        event.setCurrentStatus("ASSIGNED");
        event.setAssignTime(LocalDateTime.now());
        if (aiSuggestion != null) {
            event.setAiSuggestion(aiSuggestion);
        }
        return this.updateById(event);
    }

    public boolean acceptEvent(Long eventId) {
        GridEvent event = this.getById(eventId);
        if (event == null) return false;
        event.setCurrentStatus("ACCEPTED");
        event.setAcceptTime(LocalDateTime.now());
        return this.updateById(event);
    }

    public boolean startHandle(Long eventId) {
        GridEvent event = this.getById(eventId);
        if (event == null) return false;
        event.setCurrentStatus("HANDLING");
        event.setStartHandleTime(LocalDateTime.now());
        return this.updateById(event);
    }

    public boolean completeEvent(Long eventId, String handleResult, String handleDetail) {
        GridEvent event = this.getById(eventId);
        if (event == null) return false;
        event.setCurrentStatus("COMPLETED");
        event.setCompleteTime(LocalDateTime.now());
        event.setHandleResult(handleResult);
        event.setHandleDetail(handleDetail);
        return this.updateById(event);
    }

    public Map<String, Object> getStatistics() {
        LambdaQueryWrapper<GridEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GridEvent::getStatus, 1);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", this.count(wrapper));

        LambdaQueryWrapper<GridEvent> pendingWrapper = new LambdaQueryWrapper<>(wrapper);
        pendingWrapper.eq(GridEvent::getCurrentStatus, "PENDING");
        stats.put("pending", this.count(pendingWrapper));

        LambdaQueryWrapper<GridEvent> handlingWrapper = new LambdaQueryWrapper<>(wrapper);
        handlingWrapper.eq(GridEvent::getCurrentStatus, "HANDLING");
        stats.put("handling", this.count(handlingWrapper));

        LambdaQueryWrapper<GridEvent> completedWrapper = new LambdaQueryWrapper<>(wrapper);
        completedWrapper.eq(GridEvent::getCurrentStatus, "COMPLETED");
        stats.put("completed", this.count(completedWrapper));

        return stats;
    }

    public List<GridEvent> getByGridCellId(Long gridCellId) {
        LambdaQueryWrapper<GridEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GridEvent::getGridCellId, gridCellId);
        wrapper.orderByDesc(GridEvent::getReportTime);
        return this.list(wrapper);
    }

    public List<GridEvent> getDeadlineWarnings() {
        LambdaQueryWrapper<GridEvent> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(GridEvent::getDeadline);
        wrapper.eq(GridEvent::getCurrentStatus, "ASSIGNED");
        wrapper.or();
        wrapper.eq(GridEvent::getCurrentStatus, "ACCEPTED");
        wrapper.or();
        wrapper.eq(GridEvent::getCurrentStatus, "HANDLING");
        wrapper.apply("deadline < {0}", LocalDateTime.now().plusHours(24));
        return this.list(wrapper);
    }
}
