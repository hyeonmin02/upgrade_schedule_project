package com.schedule2.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String title;
    private final String userName;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(
            Long id,
            String title,
            String userName,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
