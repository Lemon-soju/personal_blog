package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.PostWriteResponseDto;
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
    public PostWriteResponseDto createPost(PostWriteRequestDto postWriteRequestDto, User writer) {
        log.info("글쓰기 서비스 시작");
        Post post = Post.builder()
                .title(postWriteRequestDto.getTitle())
                .content(postWriteRequestDto.getContent())
                .writer(writer)
                .createDate(LocalDateTime.now())
                .build();
        Post savedPost = postDataRepository.save(post);

        PostWriteResponseDto postWriteResponseDto = PostWriteResponseDto
                .builder()
                .postId(savedPost.getId())
                .build();

        log.info("글쓰기 서비스 종료");

        return postWriteResponseDto;
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

    @Transactional
    public void editPost(PostEditRequestDto posteditRequestDto) {
        Post findPost = postDataRepository.findById(posteditRequestDto.getId()).get();
        findPost.setTitle(posteditRequestDto.getTitle());
        findPost.setContent(posteditRequestDto.getContent());
    }
}
