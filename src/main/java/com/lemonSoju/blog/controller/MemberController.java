package com.lemonSoju.blog.controller;


import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;
import com.lemonSoju.blog.dto.response.MemberLoginResponseDto;
import com.lemonSoju.blog.dto.response.MemberSignUpResponseDto;
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
public class MemberController {

    private final UserService userService;

    @PostMapping("signup")
    public MemberSignUpResponseDto userSignUp(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        log.info("회원가입 시도");
        return userService.join(memberSignUpRequestDto);
    }

    @PostMapping("login")
    public MemberLoginResponseDto userLogin(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        log.info("로그인 시도");
        return userService.login(memberLoginRequestDto);
    }
}