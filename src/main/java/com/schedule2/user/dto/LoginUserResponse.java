package com.schedule2.user.dto;

import lombok.Getter;

@Getter
public class LoginUserResponse {
    private final Long id;
    private final String userName;

    public LoginUserResponse(Long id, String userName) {
        this.id = id;
        this.userName = userName;

    }
}
