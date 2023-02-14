package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreatePostResponseDto {

    private Long postId;

    @Builder
    public CreatePostResponseDto(Long postId) {
        this.postId = postId;
    }
}