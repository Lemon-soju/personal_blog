package com.lemonSoju.blog.controller;


import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;
import com.lemonSoju.blog.dto.response.MemberLoginResponseDto;
import com.lemonSoju.blog.dto.response.MemberSignUpResponseDto;
import com.lemonSoju.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("signup")
    public MemberSignUpResponseDto memberSignUp(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        log.info("회원가입 시도");
        return memberService.join(memberSignUpRequestDto);
    }

    @PostMapping("login")
    public MemberLoginResponseDto memberLogin(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        log.info("로그인 시도");
        return memberService.login(memberLoginRequestDto);
    }

    @GetMapping("refreshToken")
    public MemberLoginResponseDto refreshToken(@RequestHeader HttpHeaders request) {
        return memberService.refreshToken(request);
    }
}