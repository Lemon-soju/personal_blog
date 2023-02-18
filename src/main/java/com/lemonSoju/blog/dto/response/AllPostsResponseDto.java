package com.lemonSoju.blog.dto.response;

import com.lemonSoju.blog.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class AllPostsResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String writer;

    @Builder
    public AllPostsResponseDto(Long postId, String title, String content, String writer) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
