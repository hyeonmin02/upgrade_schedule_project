package com.schedule2.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String userName;
    private String Email;
    private String password;
}
