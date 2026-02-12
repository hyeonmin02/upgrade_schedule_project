package com.schedule2.user.controller;

import com.schedule2.user.dto.*;
import com.schedule2.user.service.UserService;
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
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @GetMapping // 유저 전체조회
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userid}") // 유저 단건 조회
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userid) {
        return ResponseEntity.ok(userService.getOne(userid));
    }

    @PutMapping("/{userid}") // 유저 단건 수정
    public ResponseEntity<UpdateUserResponse> update(
            @PathVariable Long userid,
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(userid,request));
    }

    @DeleteMapping("/{userid}") // 유저 단건 삭제 회원탈퇴
    public ResponseEntity<Void> delete(
            @PathVariable Long userid
    ) { userService.delete(userid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
//    @PostMapping("/signup")
//    public ResponseEntity

// 로그인할 때 포스트매핑 유저네임과 패스워드같은 데이터들을 보내줘야함
// body값이 있어야하니까 get으로 하는게 상태코드로 볼때 올바를 수 있지만 get은 조회이고 body값이 없기 때문에 PostMapping이 맞다
