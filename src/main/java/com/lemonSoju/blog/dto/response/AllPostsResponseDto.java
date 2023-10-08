package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AllPostsResponseDto {
    private List<PostInfoResponseDto> posts;
    private long totalItemsCount;

    @Builder
    public AllPostsResponseDto(List<PostInfoResponseDto> posts, long totalItemsCount) {
        this.posts = posts;
        this.totalItemsCount = totalItemsCount;
    }
}
