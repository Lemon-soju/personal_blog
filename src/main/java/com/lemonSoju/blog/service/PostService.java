package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.dto.request.PostDeleteRequestDto;
import com.lemonSoju.blog.dto.request.PostEditRequestDto;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.dto.response.AllPostsResponseDto;
import com.lemonSoju.blog.dto.response.PostInfoResponseDto;
import com.lemonSoju.blog.dto.response.PostReadResponseDto;
import com.lemonSoju.blog.dto.response.PostWriteResponseDto;
import com.lemonSoju.blog.exception.PostNotFoundException;
import com.lemonSoju.blog.exception.UnauthorizedException;
import com.lemonSoju.blog.repository.LikeDataRepository;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostDataRepository postDataRepository;
    private final JwtService jwtService;

    @Transactional
    @CacheEvict(value = "postCacheStore", allEntries = true)
    public PostWriteResponseDto createPost(PostWriteRequestDto postWriteRequestDto, Member writer) {
        Post post = Post.builder()
                .title(postWriteRequestDto.getTitle())
                .content(postWriteRequestDto.getContent())
                .writer(writer)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .imagePreview(extractImage(postWriteRequestDto.getContent()))
                .build();
        Post savedPost = postDataRepository.save(post);
        PostWriteResponseDto postWriteResponseDto = PostWriteResponseDto.builder()
                .postId(savedPost.getId())
                .build();
        return postWriteResponseDto;
    }

    public String extractImage(String content) {
        Document doc = Jsoup.parse(content);
        Element img = doc.select("img").first();
        return (img != null) ? img.attr("src") : null;
    }

    @Cacheable(value = "postCacheStore", condition = "#search == null")
    public AllPostsResponseDto getPosts(String search, String accessToken, int page, int count) {
        Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Sort.Direction.DESC, "id"));
        // 로그인 한 경우와 안한 경우 구분
        Member findMember = accessToken != null ? jwtService.findMemberByToken(accessToken) : null;

        List<Post> findPosts;
        findPosts = postDataRepository.findAllWithFetchJoin(search, null, pageable);
        List<PostInfoResponseDto> postList = matchLike(findMember, findPosts);
        long totalItemsCount = search == null ? postDataRepository.count() : postDataRepository.countBySearch(search);

        AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto.builder()
                .posts(postList)
                .totalItemsCount(totalItemsCount)
                .build();
        return allPostsResponseDto;
    }

    @Cacheable(value = "postCacheStore", condition = "#search == null")
    public AllPostsResponseDto getPostsManage(String search, String accessToken, int page, int count) {
        Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Sort.Direction.DESC, "id"));
        Member findMember = jwtService.findMemberByToken(accessToken);

        if(findMember == null) {
            throw new UnauthorizedException();
        }

        List<Post> findPosts;
        findPosts = postDataRepository.findAllWithFetchJoin(search, findMember.getUid(), pageable);
        List<PostInfoResponseDto> postList = matchLike(findMember, findPosts);
        long totalItemsCount = search == null ? postDataRepository.count() : postDataRepository.countBySearch(search);

        AllPostsResponseDto allPostsResponseDto = AllPostsResponseDto.builder()
                .posts(postList)
                .totalItemsCount(totalItemsCount)
                .build();
        return allPostsResponseDto;
    }

    public List<PostInfoResponseDto> matchLike(Member member, List<Post> posts) {
        List<PostInfoResponseDto> postList = new ArrayList<>();
        for (Post e : posts) {
            boolean isLiked = member != null ? isLiked = member.getLikes().stream()
                    .anyMatch(like -> like.getPost().getId() == e.getId()) : false;

            PostInfoResponseDto postInfoResponseDto = PostInfoResponseDto.builder()
                    .postId(e.getId())
                    .title(e.getTitle())
                    .writer(e.getWriter().getUid())
                    .createDate(e.getCreateDate())
                    .imagePreview(e.getImagePreview())
                    .isLiked(isLiked)
                    .build();
            postList.add(postInfoResponseDto);
        }
        return postList;
    }

    @CacheEvict(value = "postCacheStore", allEntries = true)
    @Transactional
    public void deletePosts(PostDeleteRequestDto postDeleteRequestDto) {
        for (Long e : postDeleteRequestDto.getCheckedInputs()) {
            postDataRepository.deleteById(e);
        }
    }

    public PostReadResponseDto readPost(Long id) {
        Optional<Post> findOptionalPost = postDataRepository.findById(id);
        if (findOptionalPost.isEmpty()) {
            throw new PostNotFoundException();
        }
        Post findPost = findOptionalPost.get();
        PostReadResponseDto postReadResponseDto = PostReadResponseDto.builder()
                .postId(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .author(findPost.getWriter().getUid())
                .createDate(findPost.getCreateDate())
                .build();
        return postReadResponseDto;
    }

    @CacheEvict(value = "postCacheStore", allEntries = true)
    @Transactional
    public void editPost(PostEditRequestDto posteditRequestDto, Long postId, Member writer) {
        Post findPost = postDataRepository.findById(postId).get();
        if (!writer.equals(findPost.getWriter())) {
            throw new UnauthorizedException();
        }
        findPost.editPost(posteditRequestDto.getTitle(), posteditRequestDto.getContent(), LocalDateTime.now(), extractImage(posteditRequestDto.getContent()));
    }
}
