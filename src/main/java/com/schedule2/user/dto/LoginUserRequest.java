package com.schedule2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginUserRequest {
    @NotBlank(message = "이메일을 입력해주세요!")
    @Email(message = "이메일 형식으로만 입력해주세요!")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요!")
    @Size(min = 8, message = "비밀번호는 8자 이상입니다.")
    private String password;

}
