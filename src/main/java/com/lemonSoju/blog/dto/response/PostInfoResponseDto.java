package com.lemonSoju.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostInfoResponseDto {
    private Long postId;
    private String title;
    private String writer;

    private LocalDateTime createDate;

    private String imagePreview;

    @JsonProperty("isLiked")

    private boolean isLiked;

    @Builder
    public PostInfoResponseDto(Long postId, String title, String writer, LocalDateTime createDate, String imagePreview, boolean isLiked) {
        this.postId = postId;
        this.title = title;
        this.writer = writer;
        this.createDate = createDate;
        this.imagePreview = imagePreview;
        this.isLiked = isLiked;
    }
}
