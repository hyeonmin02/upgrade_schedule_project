package com.schedule2.user.service;

import com.schedule2.config.PasswordEncoder;
import com.schedule2.exception.*;
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

    @Transactional // 회원가입
    public RegisterResponse register(RegisterRequest request) {

        // 회원가입 이메일 중복체크 409 에러
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("중복된 이메일입니다. 다른 이메일을 입력해주세요.");
        }

        // 회원가입 닉네임 중복체크 409 에러
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new DuplicateResourceException("중복된 닉네임입니다. 다른 닉네임을 입력해주세요.");
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUserName()
                , request.getEmail(),
                encodePassword);

        // DB에 유저정보 저장
        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    // 유저 정보 전체 조회
    public List<GetUserResponse> getAll() { // 전체조회 시 아예 일정이 없다면?
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

    // 유저 정보 단건 조회
    public GetUserResponse getOne(Long userid) { //
        User user = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        return new GetUserResponse(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }

    @Transactional // 유저 정보 수정
    public UpdateUserResponse update(Long userId, Long loginUserId, UpdateUserRequest request) {


        // 로그인이 안되어있음 401 에러
        if (loginUserId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 본인이 아닌 다른 유저정보를 수정할 순 없음 403 에러
        if (!loginUserId.equals(userId)) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        // 엥? 존재하지 않는 유저인데요 404 에러
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다."));

        String encodePassword = passwordEncoder.encode(request.getPassword());
        user.updateUser(request.getUserName(), encodePassword);

        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());

    }

    @Transactional
    public void delete(Long userId, Long loginUserId) {


        if (loginUserId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 본인이 아닌 다른 유저정보를 삭제할 순 없음 403 에러
        if (!loginUserId.equals(userId)) {
            throw new ForbiddenException("권한이 없습니다.");
        }


        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        userRepository.delete(user);
    }

    public User login(LoginUserRequest request) {
        // 사용자가 요청한 이메일을 가지고 DB에서 이메일이 같은 유저를 찾아서 없으면 예외를 던지고 있으면 user변수에 담아라
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다!"));
        // 예외문구에 이메일과 비밀번호를 포함한 이유: 사용자가 이메일만 틑렸다는 문구만 보고 오 비밀번호는 맞아? 그럼 이메일만 찾으면되겠네
        // 보안에 문제가 생김

        // DB에서 가져온 유저의 비밀번호와 요청한 패스워드가 일치하지않는가?
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()
        )) {
            // 이메일 비밀번호 틀림 401에러
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다!");
        }
        return user;
    }
}
