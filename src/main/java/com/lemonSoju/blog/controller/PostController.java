package com.lemonSoju.blog.controller;

import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.CreatePostRequestDto;
import com.lemonSoju.blog.dto.response.CreatePostResponseDto;
import com.lemonSoju.blog.service.JwtService;
import com.lemonSoju.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    @PostMapping("/user/post")
    public CreatePostResponseDto createPost(@RequestBody @Valid CreatePostRequestDto createPostRequestDto
            , @RequestHeader HttpHeaders request) {
        log.info("글쓰기 시도");
        User writer = jwtService.findUserByToken(request);
        return postService.createPost(createPostRequestDto, writer);
    }
}
