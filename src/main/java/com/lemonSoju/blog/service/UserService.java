package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.UserLoginRequestDto;
import com.lemonSoju.blog.dto.response.UserLoginResponseDto;
import com.lemonSoju.blog.dto.response.UserSignUpResponseDto;
import com.lemonSoju.blog.repository.UserDataRepository;
import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 붙은 클래스 자동으로 인젝션하여 생성자를 생성한다
@Slf4j
public class UserService {

    private final UserDataRepository userDataRepository;
    private static final String KEY = "HiThisIsLemonSojuProjectKeyValue";


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

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        List<User> findUser = userDataRepository.findByUid(userLoginRequestDto.getUid())
                .stream().filter(m -> m.getPwd().equals(userLoginRequestDto.getPwd())).collect(Collectors.toList());
        if(findUser.size() != 1) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }

        SecretKey key = Keys.hmacShaKeyFor(KEY.getBytes());

        // jwt 설정
        String jws = Jwts.builder()
                .setSubject("Joe")
                .signWith(key)
                .compact();

        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .uid(findUser.get(0).getUid())
                .accessToken(jws)
                .build();
        return userLoginResponseDto;
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