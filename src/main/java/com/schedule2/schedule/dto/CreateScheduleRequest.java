package com.schedule2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "제목을 작성해주세요!")
    @Size(max = 10, message = "최대 10자 내로 작성해주세요")
    private String title;

    @NotBlank(message = "내용을 작성해주세요!")
    @Size(max = 50, message = "일정내용을 굳이 길게 작성할 필요가있나요?")
    private String content;



}
