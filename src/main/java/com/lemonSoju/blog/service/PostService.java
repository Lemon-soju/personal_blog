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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostDataRepository postDataRepository;

    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, User writer) {

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

        return createPostResponseDto;
    }
}
