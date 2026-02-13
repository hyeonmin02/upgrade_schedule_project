package com.schedule2.user.repository;

import com.schedule2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Optional = 너가 찾아달라던 이메일이 있을수도 없을수도있다? 너가 보고 없으면 OrElseThrow 하셈
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); // 회원가입 시 이메일 중복체크

    boolean existsByUserName(String userName); // 회원가입 시 닉네임 중복체크
}
