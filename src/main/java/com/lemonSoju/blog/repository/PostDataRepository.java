package com.lemonSoju.blog.repository;

import com.lemonSoju.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDataRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String search);
}