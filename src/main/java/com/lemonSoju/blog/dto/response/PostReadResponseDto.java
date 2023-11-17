package com.lemonSoju.blog.dto.response;

import com.lemonSoju.blog.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostReadResponseDto {

    private Long postId;
    private String title;
    private String content;

    private String writer;
    private LocalDateTime createDate;

    @Builder
    public PostReadResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter().getUid();
        this.createDate = post.getCreateDate();
    }
}