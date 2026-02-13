package com.schedule2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Size(max = 4, message = "닉네임은 최대 4글자만 가능합니다!")
    private String userName;

    @Email(message = "이메일 형식으로만 입력해주세요!")
    private String Email;

    @Size(min = 8, message = "비밀번호는 8자 이상부터 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
            ,message = "비밀번호는 영문 ,숫자, 특수문자를 포함한 8자 이상이어야합니다.")
    private String password;
}
