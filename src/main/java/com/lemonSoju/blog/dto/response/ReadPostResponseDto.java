package com.lemonSoju.blog.dto.response;

import com.lemonSoju.blog.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadPostResponseDto{

    private Long postId;
    private String title;
    private String content;

    private String author;
    private LocalDateTime createDate;

    @Builder
    public ReadPostResponseDto(Long postId, String title, String content, String author, LocalDateTime createDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate =  createDate;
    }
}