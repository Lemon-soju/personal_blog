package com.lemonSoju.blog.controller;

import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.CreatePostRequestDto;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.CreatePostResponseDto;
import com.lemonSoju.blog.dto.response.ReadPostResponseDto;
import com.lemonSoju.blog.service.JwtService;
import com.lemonSoju.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/post")
    public List<AllPostsResponseDto> getPost() {
        log.info("getPost 실행");
        return postService.getPostService();
    }

    @PostMapping("/user/post/delete")
    public void deletePosts(@RequestBody DeletePostRequestDto deletePostRequestDto) {
        postService.deletePosts(deletePostRequestDto);
    }

    @GetMapping("/post/detail")
    public ReadPostResponseDto readPost(@RequestParam Long id) {
        log.info("글 detail 읽기 시도");
        return postService.readPost(id);
    }

    @PostMapping("/user/post/edit/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody PostEditRequestDto postEditRequestDto) {
        postService.editPost(postEditRequestDto);
    }
}
