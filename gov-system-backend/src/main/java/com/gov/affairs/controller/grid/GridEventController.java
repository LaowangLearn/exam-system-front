package com.gov.affairs.controller.grid;

import com.gov.affairs.common.R;
import com.gov.affairs.common.PageResult;
import com.gov.affairs.entity.grid.GridEvent;
import com.gov.affairs.service.grid.GridEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grid/event")
public class GridEventController {

    @Autowired
    private GridEventService gridEventService;

    @GetMapping("/page")
    public R<PageResult<GridEvent>> pageQuery(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String eventTitle,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String currentStatus,
            @RequestParam(required = false) String urgentLevel,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(gridEventService.pageQuery(pageNum, pageSize, eventTitle, eventType, currentStatus, urgentLevel, startDate, endDate));
    }

    @GetMapping("/{eventId}")
    public R<GridEvent> getById(@PathVariable Long eventId) {
        return R.ok(gridEventService.getById(eventId));
    }

    @GetMapping("/grid/{gridCellId}")
    public R<List<GridEvent>> getByGridCellId(@PathVariable Long gridCellId) {
        return R.ok(gridEventService.getByGridCellId(gridCellId));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(gridEventService.getStatistics());
    }

    @GetMapping("/deadline/warnings")
    public R<List<GridEvent>> getDeadlineWarnings() {
        return R.ok(gridEventService.getDeadlineWarnings());
    }

    @PostMapping
    public R<Boolean> create(@RequestBody GridEvent gridEvent) {
        gridEvent.setCurrentStatus("PENDING");
        return R.ok(gridEventService.save(gridEvent));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody GridEvent gridEvent) {
        return R.ok(gridEventService.updateById(gridEvent));
    }

    @DeleteMapping("/{eventId}")
    public R<Boolean> delete(@PathVariable Long eventId) {
        return R.ok(gridEventService.removeById(eventId));
    }

    @PutMapping("/assign/{eventId}")
    public R<Boolean> assignEvent(@PathVariable Long eventId,
                                  @RequestParam Long handleDepartmentId,
                                  @RequestParam String handleDepartmentName,
                                  @RequestParam(required = false) String aiSuggestion) {
        return R.ok(gridEventService.assignEvent(eventId, handleDepartmentId, handleDepartmentName, aiSuggestion));
    }

    @PutMapping("/accept/{eventId}")
    public R<Boolean> acceptEvent(@PathVariable Long eventId) {
        return R.ok(gridEventService.acceptEvent(eventId));
    }

    @PutMapping("/start/{eventId}")
    public R<Boolean> startHandle(@PathVariable Long eventId) {
        return R.ok(gridEventService.startHandle(eventId));
    }

    @PutMapping("/complete/{eventId}")
    public R<Boolean> completeEvent(@PathVariable Long eventId,
                                    @RequestParam String handleResult,
                                    @RequestParam(required = false) String handleDetail) {
        return R.ok(gridEventService.completeEvent(eventId, handleResult, handleDetail));
    }

    @PutMapping("/evaluate/{eventId}")
    public R<Boolean> evaluateEvent(@PathVariable Long eventId,
                                    @RequestParam Integer satisfactionScore,
                                    @RequestParam(required = false) String citizenFeedback) {
        GridEvent event = gridEventService.getById(eventId);
        if (event == null) return R.fail("事件不存在");
        event.setSatisfactionScore(satisfactionScore);
        event.setCitizenFeedback(citizenFeedback);
        event.setEvaluationTime(java.time.LocalDateTime.now());
        event.setCurrentStatus("CLOSED");
        return R.ok(gridEventService.updateById(event));
    }
}
