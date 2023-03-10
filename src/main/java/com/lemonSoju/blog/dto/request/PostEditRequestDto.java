package com.lemonSoju.blog.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostEditRequestDto {

    private Long id;
    private String title;
    private String content;

    @Builder
    public PostEditRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}