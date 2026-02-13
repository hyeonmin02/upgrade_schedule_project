package com.schedule2.schedule.controller;

import com.schedule2.schedule.dto.*;
import com.schedule2.schedule.service.ScheduleService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CreateScheduleResponse> create(
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId,
            @Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(loginUserId, request));
    }

    @GetMapping // 일정 전체 조회
    public ResponseEntity<List<GetScheduleResponse>> checkSchedules() {
        return ResponseEntity.ok(scheduleService.checkSchedules());
    }

    @GetMapping("/{scheduleId}")// 일정 단건 조회
    public ResponseEntity<GetScheduleResponse> checkSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.checkSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}") // 일정 단건 수정
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {

        return ResponseEntity.ok(scheduleService.updateSchedule(loginUserId, scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}") // 일정 단건 삭제
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId,
            @PathVariable Long scheduleId) {

        scheduleService.deleteSchedule(loginUserId, scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
