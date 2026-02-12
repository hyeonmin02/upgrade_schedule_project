package com.schedule2.schedule.controller;

import com.schedule2.schedule.dto.*;
import com.schedule2.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping // 일정 생성
    public ResponseEntity<CreateScheduleResponse> create(@RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request));
    }

    @GetMapping // 일정 전체 조회
    public ResponseEntity<List<GetScheduleResponse>> checkSchedules() {
        return ResponseEntity.ok(scheduleService.checkSchedules());
    }

    @GetMapping("/{scheduleId}")// 일정 단건 조회
    public ResponseEntity<GetScheduleResponse> checkSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.checkSchedule(scheduleId));
    }

    @PutMapping("/{scheduleId}") // 일정 단건 수정
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}") // 일정 단건 삭제
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId)
    {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
