package com.lemonSoju.blog.repository;


import com.lemonSoju.blog.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDataRepository extends JpaRepository<Like, Long> {
}
