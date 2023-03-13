package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class PostWriteResponseDto {

    private Long postId;

    @Builder
    public PostWriteResponseDto(Long postId) {
        this.postId = postId;
    }
}