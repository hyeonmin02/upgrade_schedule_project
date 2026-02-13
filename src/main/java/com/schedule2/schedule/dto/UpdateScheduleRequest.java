package com.schedule2.schedule.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @Size(max = 10, message = "10자 내로 입력해주세요.")
    private String title;

    @Size(max = 50, message = "내용이 그렇게 긴가요?" )
    private String content;
}
