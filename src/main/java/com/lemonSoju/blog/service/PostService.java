package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.dto.request.PostDeleteRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.PostWriteResponseDto;
import com.lemonSoju.blog.dto.response.PostReadResponseDto;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PostWriteResponseDto createPost(PostWriteRequestDto postWriteRequestDto, Member writer) {
        Post post = Post.builder().title(postWriteRequestDto.getTitle()).content(postWriteRequestDto.getContent()).writer(writer).createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).imagePreview(extractImage(postWriteRequestDto.getContent())).build();
        Post savedPost = postDataRepository.save(post);
        PostWriteResponseDto postWriteResponseDto = PostWriteResponseDto.builder().postId(savedPost.getId()).build();
        return postWriteResponseDto;
    }

    public String extractImage(String content) {
        Document doc = Jsoup.parse(content);
        Element img = doc.select("img").first();
        return (img != null) ? img.attr("src") : null;
    }

    public List<AllPostsResponseDto> getPostService(String search) {
        List<Post> findPosts = (search == null) ? postDataRepository.findAll() : postDataRepository.findAllByTitleContaining(search);
        List<AllPostsResponseDto> postList = new ArrayList<>();
        for (Post e : findPosts) {
            AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto.builder().postId(e.getId()).title(e.getTitle()).writer(e.getWriter().getUid()).createDate(e.getCreateDate()).imagePreview(e.getImagePreview()).build();
            postList.add(allPostsResponseDto);
        }
        Collections.sort(postList, Comparator.comparing(AllPostsResponseDto::getCreateDate));
        return postList;
    }

    @Transactional
    public void deletePosts(PostDeleteRequestDto postDeleteRequestDto) {
        for (Long e : postDeleteRequestDto.getCheckedInputs()) {
            postDataRepository.deleteById(e);
        }
    }

    public PostReadResponseDto readPost(Long id) {
        Post findPost = postDataRepository.findById(id).get();
        PostReadResponseDto postReadResponseDto = PostReadResponseDto.builder().postId(findPost.getId()).title(findPost.getTitle()).content(findPost.getContent()).author(findPost.getWriter().getUid()).createDate(findPost.getCreateDate()).build();
        return postReadResponseDto;
    }

    @Transactional
    public void editPost(PostEditRequestDto posteditRequestDto, Long postId) {
        Post findPost = postDataRepository.findById(postId).get();
        findPost.editPost(posteditRequestDto.getTitle(), posteditRequestDto.getContent(), LocalDateTime.now(), extractImage(posteditRequestDto.getContent()));
    }
}
