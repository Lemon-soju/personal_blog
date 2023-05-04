package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.PostWriteResponseDto;
import com.lemonSoju.blog.dto.response.ReadPostResponseDto;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostDataRepository postDataRepository;

    @Transactional
    public PostWriteResponseDto createPost(PostWriteRequestDto postWriteRequestDto, Member writer)  {
        log.info("글쓰기 서비스 시작");
        Post post = Post.builder()
                .title(postWriteRequestDto.getTitle())
                .content(postWriteRequestDto.getContent())
                .writer(writer)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .imagePreview(extractImage(postWriteRequestDto.getContent()))
                .build();
        Post savedPost = postDataRepository.save(post);

        PostWriteResponseDto postWriteResponseDto = PostWriteResponseDto
                .builder()
                .postId(savedPost.getId())
                .build();

        log.info("글쓰기 서비스 종료");

        return postWriteResponseDto;
    }

    public String extractImage(String content)  {
        Document doc = Jsoup.parse(content);
        Element img = doc.select("img").first();
        if (img != null) {
            return img.attr("src");
        }
        return null;
    }

    public List<AllPostsResponseDto> getPostService() {
        List<Post> findPosts = postDataRepository.findAll();
        List<AllPostsResponseDto> postList = new ArrayList<>();
        for (Post e : findPosts) {
            AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto
                    .builder()
                    .postId(e.getId())
                    .title(e.getTitle())
                    .content(e.getContent().substring(0, Math.min(e.getContent().length(), 30)))
                    .writer(e.getWriter().getUid())
                    .createDate(e.getCreateDate())
                    .imagePreview(e.getImagePreview())
                    .build();
            postList.add(allPostsResponseDto);
        }
        Collections.sort(postList, Comparator.comparing(AllPostsResponseDto::getCreateDate));
        return postList;
    }

    public List<AllPostsResponseDto> getPostBySearch(String search) {
        List<Post> findPosts = postDataRepository.findAllByTitleContaining(search);
        List<AllPostsResponseDto> postList = new ArrayList<>();
        for (Post e : findPosts) {
            AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto
                    .builder()
                    .postId(e.getId())
                    .title(e.getTitle())
                    .content(e.getContent().substring(0, Math.min(e.getContent().length(), 30)))
                    .writer(e.getWriter().getUid())
                    .createDate(e.getCreateDate())
                    .imagePreview(e.getImagePreview())
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
                .author(findPost.getWriter().getUid())
                .createDate(findPost.getCreateDate())
                .build();
        return readPostResponseDto;
    }

    @Transactional
    public void editPost(PostEditRequestDto posteditRequestDto) {
        Post findPost = postDataRepository.findById(posteditRequestDto.getId()).get();
        findPost.setTitle(posteditRequestDto.getTitle());
        findPost.setContent(posteditRequestDto.getContent());
        findPost.setUpdateDate(LocalDateTime.now());
        findPost.setImagePreview(extractImage(posteditRequestDto.getContent()));
    }
}
