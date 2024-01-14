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

import static com.lemonSoju.blog.service.JwtService.ACCESS_TOKEN;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("signup")
    public MemberSignUpResponseDto memberSignUp(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto) {
        return memberService.join(memberSignUpRequestDto);
    }

    @PostMapping("login")
    public MemberLoginResponseDto memberLogin(@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto) {
        return memberService.login(memberLoginRequestDto);
    }

    @GetMapping("auth/refreshToken")
    public MemberLoginResponseDto refreshToken(@RequestHeader HttpHeaders headers) {
        return memberService.refreshToken(headers.getFirst(ACCESS_TOKEN));
    }
}