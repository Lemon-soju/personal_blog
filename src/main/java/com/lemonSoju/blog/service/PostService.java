package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.CreatePostRequestDto;
import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
import com.lemonSoju.blog.dto.response.CreatePostResponseDto;
import com.lemonSoju.blog.dto.response.UserSignUpResponseDto;
import com.lemonSoju.blog.repository.PostDataRepository;
import com.lemonSoju.blog.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostDataRepository postDataRepository;

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, User writer) {
        log.info("글쓰기 서비스 시작");
        Post post = Post.builder()
                .title(createPostRequestDto.getTitle())
                .content(createPostRequestDto.getContent())
                .writer(writer)
                .build();
        Post savedPost = postDataRepository.save(post);

        CreatePostResponseDto createPostResponseDto = CreatePostResponseDto
                .builder()
                .postId(savedPost.getId())
                .build();

        log.info("글쓰기 서비스 종료");

        return createPostResponseDto;
    }
}
