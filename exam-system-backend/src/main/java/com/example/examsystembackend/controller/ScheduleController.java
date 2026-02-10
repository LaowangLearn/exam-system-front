package com.example.examsystembackend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.examsystembackend.entity.Schedule;
import com.example.examsystembackend.entity.ScheduleParticipant;
import com.example.examsystembackend.service.ScheduleService;
import com.example.examsystembackend.service.ScheduleParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oa/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleParticipantService scheduleParticipantService;

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.list();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getById(id);
    }

    @PostMapping
    public boolean createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    @PutMapping("/{id}")
    public boolean updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        schedule.setId(id);
        return scheduleService.updateById(schedule);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSchedule(@PathVariable Long id) {
        return scheduleService.removeById(id);
    }

    @PostMapping("/participants")
    public boolean addParticipant(@RequestBody ScheduleParticipant participant) {
        return scheduleParticipantService.save(participant);
    }

    @DeleteMapping("/participants/{id}")
    public boolean removeParticipant(@PathVariable Long id) {
        return scheduleParticipantService.removeById(id);
    }
}
