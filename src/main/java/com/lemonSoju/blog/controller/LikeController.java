package com.lemonSoju.blog.controller;

import com.lemonSoju.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lemonSoju.blog.service.JwtService.ACCESS_TOKEN;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("like/post/{postId}")
    public ResponseEntity createLike(@PathVariable Long postId, @RequestHeader HttpHeaders headers) {
        return likeService.createLike(postId, headers.getFirst(ACCESS_TOKEN));
    }

    @DeleteMapping("like/post/{postId}")
    public ResponseEntity deleteLike(@PathVariable Long postId, @RequestHeader HttpHeaders headers) {
        return likeService.deleteLike(postId, headers.getFirst(ACCESS_TOKEN));
    }
}