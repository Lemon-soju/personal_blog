package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.CreatePostRequestDto;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.CreatePostResponseDto;
import com.lemonSoju.blog.dto.response.ReadPostResponseDto;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .createDate(LocalDateTime.now())
                .build();
        Post savedPost = postDataRepository.save(post);

        CreatePostResponseDto createPostResponseDto = CreatePostResponseDto
                .builder()
                .postId(savedPost.getId())
                .build();

        log.info("글쓰기 서비스 종료");

        return createPostResponseDto;
    }

    public List<AllPostsResponseDto> getPostService() {
        List<Post> findPosts = postDataRepository.findAll();
        List<AllPostsResponseDto> postList = new ArrayList<>();
        for (Post e : findPosts) {
            AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto
                    .builder()
                    .postId(e.getId())
                    .title(e.getTitle())
                    .content(e.getContent())
                    .writer(e.getWriter().getUid())
                    .createDate(e.getCreateDate())
                    .build();
            postList.add(allPostsResponseDto);
        }
        return postList;
    }

    @Transactional
    public void deletePosts(DeletePostRequestDto deletePostRequestDto) {
        for (Long e : deletePostRequestDto.getCheckedInputs()) {
            postDataRepository.deleteById(e);
        }
    }

    public ReadPostResponseDto readPost(Long id) {
        Post findPost = postDataRepository.findById(id).get();
        ReadPostResponseDto readPostResponseDto= ReadPostResponseDto.builder()
                .postId(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .build();
        return readPostResponseDto;
    }
}
