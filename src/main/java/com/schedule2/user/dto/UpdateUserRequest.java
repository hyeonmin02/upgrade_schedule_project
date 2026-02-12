package com.schedule2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank
    @Size(max = 4)
    private String userName;
    @NotBlank
    @Email
    private String Email;
    @NotBlank
    @Size(min = 8)
    private String password;
}
