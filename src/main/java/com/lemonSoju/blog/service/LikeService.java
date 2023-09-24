package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Like;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.dto.response.OkResponseDto;
import com.lemonSoju.blog.repository.LikeDataRepository;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final PostDataRepository postDataRepository;
    private final LikeDataRepository likeDataRepository;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<OkResponseDto> createLike(Long postId, String accessToken) {
        Member findMember = jwtService.findMemberByToken(accessToken);
        Post findPost = postDataRepository.findById(postId).get();
        Optional<Like> findLike = likeDataRepository.findByPostIdAndMemberId(findPost.getId(), findMember.getId());
        if (!findLike.isPresent()) {
            Like like = new Like();
            like.addMemberAndPost(findPost, findMember);
            likeDataRepository.save(like);
        }
        return ResponseEntity.ok(new OkResponseDto());
    }

    @Transactional
    public ResponseEntity<OkResponseDto> deleteLike(Long postId, String accessToken) {
        Member findMember = jwtService.findMemberByToken(accessToken);
        Like like = likeDataRepository.findByPostIdAndMemberId(postId, findMember.getId()).get();
        likeDataRepository.delete(like);
        return ResponseEntity.ok(new OkResponseDto());
    }
}
