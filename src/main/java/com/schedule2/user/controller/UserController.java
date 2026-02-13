package com.schedule2.user.controller;

import com.schedule2.user.dto.*;
import com.schedule2.user.entity.User;
import com.schedule2.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping // 유저 생성 회원가입
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login") // 유저 로그인
    public ResponseEntity<LoginUserResponse> login(
            @Valid @RequestBody LoginUserRequest request, HttpSession session) {

        User user = userService.login(request);

        session.setAttribute("loginUser", user.getId());

        return ResponseEntity.ok(new LoginUserResponse(user.getId(), user.getUserName()));
    }

    @PostMapping("/logout") // 유저 로그아웃
    public ResponseEntity<Void> logout(HttpSession httpSession) {

        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping // 유저 전체조회
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userId}") // 유저 단건 조회
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getOne(userId));
    }

    @PatchMapping("/{userId}") // 유저 단건 수정
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(userService.update(userId, loginUserId, request));
    }

    @DeleteMapping("/{userId}") // 유저 회원탈퇴
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId,
            @PathVariable Long userId
    ) {
        userService.delete(userId, loginUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}