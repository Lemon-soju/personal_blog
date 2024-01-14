package com.lemonSoju.blog.repository;

import com.lemonSoju.blog.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeDataRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndMemberId(Long postId, Long memberId);
}
