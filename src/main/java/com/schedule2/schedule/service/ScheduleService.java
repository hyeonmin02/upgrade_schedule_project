package com.schedule2.schedule.service;

import com.schedule2.schedule.dto.*;
import com.schedule2.schedule.entity.Schedule;
import com.schedule2.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getUserName()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUserName(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());
    }

    public List<GetScheduleResponse> checkSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream().map(
                schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getUserName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt())
        ).toList();
    }

    public GetScheduleResponse checkSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄입니다."));

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUserName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄입니다."));

        schedule.update(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence) {
            throw new IllegalStateException("없는 스케줄입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
