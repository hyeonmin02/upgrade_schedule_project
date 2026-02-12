package com.schedule2.user.entity;

import com.schedule2.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10) // 회원가입 시 닉네임 필수값, 중복 불가능
    private String userName;

    @Column(nullable = false, unique = true) // 회원가입 시 Email은 필수값, 같은 이메일 사용불가
    private String email;

    @Column(nullable = false, length = 8) // 회원가입 시 비밀번호 필수값
    private String password;

    // 유저 생성
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // 유저 정보 수정
    public void updateUser (String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}

