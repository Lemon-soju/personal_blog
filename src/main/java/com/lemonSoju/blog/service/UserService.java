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

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDataRepository userDataRepository;
    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";


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

        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode((KEY)));

        // jwt 설정
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofMinutes(30).toMillis()); // 만료기간 30분

        String jws = Jwts.builder()
                .setSubject(findUser.get(0).getUid())
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .signWith(key) // 사용자 uid
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