package com.lemonSoju.blog.controller;


import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
import com.lemonSoju.blog.dto.response.UserSignUpResponseDto;
import com.lemonSoju.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("user/signup")
    public UserSignUpResponseDto userSignUp(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        log.info("회원가입 시도");
        return userService.join(userSignUpRequestDto);
    }
}