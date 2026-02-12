package com.schedule2.schedule.controller;

import com.schedule2.schedule.dto.*;
import com.schedule2.schedule.service.ScheduleService;
import com.schedule2.user.dto.SessionUser;
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
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser,
            @Valid @RequestBody CreateScheduleRequest request) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

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

    @PatchMapping("/{scheduleId}") // 일정 단건 수정
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}") // 일정 단건 삭제
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser loginUser,
            @PathVariable Long scheduleId) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
