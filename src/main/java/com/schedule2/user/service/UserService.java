package com.schedule2.user.service;

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

    public CreateUserResponse create(CreateUserRequest request) {

        User user = new User(
                request.getUserName()
                , request.getEmail()
                , request.getPassword());

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
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
                        user.getId()
                        , user.getUserName()
                        , user.getEmail()
                        , user.getCreatedAt()
                        , user.getModifiedAt())).toList();
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

    public UpdateUserResponse update(Long userid, UpdateUserRequest request) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다."));

        user.updateUser(request.getUserName(), request.getEmail(), request.getPassword());

        return new UpdateUserResponse(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());

    }

    public void delete(Long userId) {

        boolean existence = userRepository.existsById(userId);
        if (!existence) throw new IllegalStateException("없는 유저입니다.");
        userRepository.deleteById(userId);


    }
}
