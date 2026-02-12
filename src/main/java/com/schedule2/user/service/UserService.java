package com.schedule2.user.service;

import com.schedule2.config.PasswordEncoder;
import com.schedule2.user.dto.*;
import com.schedule2.user.entity.User;
import com.schedule2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getUserName()
                , request.getEmail(),
                encodePassword);

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(
                        user -> new GetUserResponse(
                                user.getId(),
                                user.getUserName(),
                                user.getEmail(),
                                user.getCreatedAt(),
                                user.getModifiedAt()))
                .toList();
    }

    public GetUserResponse getOne(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        return new GetUserResponse(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }

    @Transactional
    public UpdateUserResponse update(Long userid, UpdateUserRequest request) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다."));

        user.updateUser(request.getUserName(), request.getPassword());

        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());

    }

    @Transactional
    public void delete(Long userId) {

        boolean existence = userRepository.existsById(userId);
        if (!existence) throw new IllegalStateException("없는 유저입니다.");
        userRepository.deleteById(userId);


    }

    public User login(LoginUserRequest request) {
        // 사용자가 요청한 이메일을 가지고 DB에서 이메일이 같은 유저를 찾아서 없으면 예외를 던지고 있으면 user변수에 담아라
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("이메일 또는 비밀번호가 올바르지 않습니다!"));

        // DB에서 가져온 유저의 비밀번호와 요청한 패스워드가 일치하지않는가?
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()
        )) {
            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다!");
        }
        return user;
    }
}
