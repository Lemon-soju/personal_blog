package com.lemonSoju.blog.controller;


import com.lemonSoju.blog.config.UserSession;
import com.lemonSoju.blog.dto.request.UserLoginRequestDto;
import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
import com.lemonSoju.blog.dto.response.UserLoginResponseDto;
import com.lemonSoju.blog.dto.response.UserSignUpResponseDto;
import com.lemonSoju.blog.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Key;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("test")
    public String test2(UserSession userSession) {
        return userSession.name;
    }

    @PostMapping("signup")
    public UserSignUpResponseDto userSignUp(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        log.info("회원가입 시도");
        return userService.join(userSignUpRequestDto);
    }

    @PostMapping("login")
    public UserLoginResponseDto userLogin(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        log.info("로그인 시도");
        return userService.login(userLoginRequestDto);
    }
}