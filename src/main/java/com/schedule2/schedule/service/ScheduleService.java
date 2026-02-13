package com.schedule2.schedule.service;

import com.schedule2.schedule.dto.*;
import com.schedule2.schedule.entity.Schedule;
import com.schedule2.schedule.repository.ScheduleRepository;
import com.schedule2.user.entity.User;
import com.schedule2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse createSchedule(Long loginUserId, CreateScheduleRequest request) {

        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다."));

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                user.getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());
    }

    public List<GetScheduleResponse> checkSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream().map(
                schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getUser().getUserName(),
                        schedule.getContent(),
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
                schedule.getUser().getUserName(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long loginUserId, Long scheduleId, UpdateScheduleRequest request) {

        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄입니다."));

        schedule.update(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getUser().getUserName(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional
    public void deleteSchedule(Long loginUserId, Long scheduleId) {
        if (loginUserId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence) {
            throw new IllegalStateException("없는 스케줄입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
