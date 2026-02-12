package com.schedule2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotBlank
    @Size(max = 4)
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상입니다.")
    private String password;

}
