package com.lemonSoju.blog.controller;

import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.PostWriteResponseDto;
import com.lemonSoju.blog.dto.response.ReadPostResponseDto;
import com.lemonSoju.blog.service.JwtService;
import com.lemonSoju.blog.service.PostService;
import com.lemonSoju.blog.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;
    private final S3UploadService s3UploadService;

    @PostMapping("auth/post")
    public PostWriteResponseDto createPost(@RequestBody @Valid PostWriteRequestDto postWriteRequestDto
            , @RequestHeader HttpHeaders request) {
        Member writer = jwtService.findMemberByToken(request);
        return postService.createPost(postWriteRequestDto, writer);
    }

    @GetMapping("post")
    public List<AllPostsResponseDto> getPost(@RequestParam(name = "search", required = false) String search) {
        if (search == null) return postService.getPostService();
        return postService.getPostBySearch(search);
    }

    @PostMapping("auth/post/delete")
    public void deletePosts(@RequestBody DeletePostRequestDto deletePostRequestDto) {
        postService.deletePosts(deletePostRequestDto);
    }

    @GetMapping("post/{postId}")
    public ReadPostResponseDto readPost(@PathVariable Long postId) {
        return postService.readPost(postId);
    }

    @PatchMapping("auth/post/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody PostEditRequestDto postEditRequestDto) {
        postService.editPost(postEditRequestDto, postId);
    }

    @PostMapping("auth/uploadImage")
    public String uploadImage(@RequestPart("img")MultipartFile file) throws IOException {
        return s3UploadService.saveFile(file);
    }
}
