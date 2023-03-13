package com.lemonSoju.blog.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostWriteRequestDto {
    @NotEmpty(message = "제목은 필수입니다.")
    private String title;
    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String content;

    @Builder
    public PostWriteRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}