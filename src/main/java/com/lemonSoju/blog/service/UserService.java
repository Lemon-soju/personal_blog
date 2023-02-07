package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.response.UserJoinDtoResponse;
import com.lemonSoju.blog.repository.UserDataRepository;
import com.lemonSoju.blog.dto.request.UserJoinDtoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 붙은 클래스 자동으로 인젝션하여 생성자를 생성한다
@Slf4j
public class UserService {

    private final UserDataRepository userDataRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public UserJoinDtoResponse join(UserJoinDtoRequest userJoinDtoRequest) {
        validateDuplicateUser(userJoinDtoRequest);
        User savedUser = userDataRepository.save(createUser(userJoinDtoRequest));
        UserJoinDtoResponse userJoinDtoResponse = UserJoinDtoResponse.builder()
                .uid(savedUser.getUid())
                .build();
        return userJoinDtoResponse;
    }

    private void validateDuplicateUser(UserJoinDtoRequest userJoinDtoRequest) {
        Optional<User> findUsers = userDataRepository.findByUid(userJoinDtoRequest.getUid());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("Already Existing User");
        }
    }

    /**
     * User 객체 생성
     */
    public User createUser(UserJoinDtoRequest userJoinDtoRequest) {
        User user = User.builder()
                .uid(userJoinDtoRequest.getUid())
                .pwd(userJoinDtoRequest.getPwd())
                .name(userJoinDtoRequest.getName())
                .build();
        return user;
    }
}