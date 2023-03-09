package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ReadPostResponseDto{

    private Long postId;
    private String title;
    private String content;

    @Builder
    public ReadPostResponseDto(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}