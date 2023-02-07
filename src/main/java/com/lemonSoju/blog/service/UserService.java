package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.response.UserSignUpResponseDto;
import com.lemonSoju.blog.repository.UserDataRepository;
import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
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
    public UserSignUpResponseDto join(UserSignUpRequestDto userSignUpRequestDto) {
        validateDuplicateUser(userSignUpRequestDto);
        User savedUser = userDataRepository.save(createUser(userSignUpRequestDto));
        UserSignUpResponseDto userSignUpResponseDto = UserSignUpResponseDto.builder()
                .uid(savedUser.getUid())
                .build();
        return userSignUpResponseDto;
    }

    private void validateDuplicateUser(UserSignUpRequestDto userSignUpRequestDto) {
        Optional<User> findUsers = userDataRepository.findByUid(userSignUpRequestDto.getUid());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("Already Existing User");
        }
    }

    /**
     * User 객체 생성
     */
    public User createUser(UserSignUpRequestDto userSignUpRequestDto) {
        User user = User.builder()
                .uid(userSignUpRequestDto.getUid())
                .pwd(userSignUpRequestDto.getPwd())
                .name(userSignUpRequestDto.getName())
                .authority("ROLE_USER")
                .build();
        return user;
    }
}