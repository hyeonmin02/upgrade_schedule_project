package com.schedule2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank//(message = "제목은 입력해주세요!")
    @Size(max = 20)// message = "최대 20자 내로 작성해주세요")
    private String title;
    @NotBlank//(message = "내용은 입력해주세요!")
    @Size(max = 100)// message = "일정내용을 굳이 길게 작성할 필요가있나요?")
    private String content;
    @NotBlank @Size(max=10)
    private String userName;
}
