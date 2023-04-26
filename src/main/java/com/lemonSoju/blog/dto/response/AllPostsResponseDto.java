package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AllPostsResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String writer;

    private LocalDateTime createDate;

    @Builder
    public AllPostsResponseDto(Long postId, String title, String content, String writer, LocalDateTime createDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createDate = createDate;
    }
}
