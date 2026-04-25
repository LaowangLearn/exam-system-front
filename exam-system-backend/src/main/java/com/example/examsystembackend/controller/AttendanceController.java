package com.example.examsystembackend.controller;

import com.example.examsystembackend.entity.Attendance;
import com.example.examsystembackend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public List<Attendance> getAllAttendances() {
        return attendanceService.list();
    }

    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getById(id);
    }

    @PostMapping
    public boolean createAttendance(@RequestBody Attendance attendance) {
        return attendanceService.save(attendance);
    }

    @PutMapping("/{id}")
    public boolean updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        attendance.setId(id);
        return attendanceService.updateById(attendance);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAttendance(@PathVariable Long id) {
        return attendanceService.removeById(id);
    }
}
