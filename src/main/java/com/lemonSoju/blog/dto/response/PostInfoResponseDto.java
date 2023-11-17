package com.lemonSoju.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lemonSoju.blog.domain.Post;
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
    public PostInfoResponseDto(Post post, boolean isLiked) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter().getUid();
        this.createDate = post.getCreateDate();
        this.imagePreview = post.getImagePreview();
        this.isLiked = isLiked;
    }
}
