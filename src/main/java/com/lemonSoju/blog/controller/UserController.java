package com.lemonSoju.blog.controller;


import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.UserJoinRequestDto;
import com.lemonSoju.blog.dto.response.UserJoinResponseDto;
import com.lemonSoju.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("user/join")
    public UserJoinResponseDto userJoin(@RequestBody @Valid UserJoinRequestDto userJoinRequestDto) {
        return userService.join(userJoinRequestDto);
    }
}