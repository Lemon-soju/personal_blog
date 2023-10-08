package com.lemonSoju.blog.repository;

import com.lemonSoju.blog.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDataRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String search);

    @Query("SELECT p FROM Post p JOIN FETCH p.writer WHERE (:search IS NULL OR p.title LIKE %:search%) AND (:writer IS NULL OR p.writer.uid = :writer)")
    List<Post> findAllWithFetchJoin(String search, String writer, Pageable pageable);

    long count();

    @Query("SELECT COUNT(p) FROM Post p WHERE :search IS NULL OR p.title LIKE %:search%")
    long countBySearch(String search);
}