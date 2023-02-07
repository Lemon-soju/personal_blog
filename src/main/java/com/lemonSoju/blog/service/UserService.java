package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.response.UserJoinResponseDto;
import com.lemonSoju.blog.repository.UserDataRepository;
import com.lemonSoju.blog.dto.request.UserJoinRequestDto;
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
    public UserJoinResponseDto join(UserJoinRequestDto userJoinRequestDto) {
        validateDuplicateUser(userJoinRequestDto);
        User savedUser = userDataRepository.save(createUser(userJoinRequestDto));
        UserJoinResponseDto userJoinResponseDto = UserJoinResponseDto.builder()
                .uid(savedUser.getUid())
                .build();
        return userJoinResponseDto;
    }

    private void validateDuplicateUser(UserJoinRequestDto userJoinRequestDto) {
        Optional<User> findUsers = userDataRepository.findByUid(userJoinRequestDto.getUid());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("Already Existing User");
        }
    }

    /**
     * User 객체 생성
     */
    public User createUser(UserJoinRequestDto userJoinRequestDto) {
        User user = User.builder()
                .uid(userJoinRequestDto.getUid())
                .pwd(userJoinRequestDto.getPwd())
                .name(userJoinRequestDto.getName())
                .authority("ROLE_USER")
                .build();
        return user;
    }
}