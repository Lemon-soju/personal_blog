package com.lemonSoju.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserSignUpResponseDto {

    @NotEmpty(message = "회원 id는 필수입니다.")
    private String uid;

    @Builder
    public UserSignUpResponseDto(String uid) {
        this.uid = uid;
    }
}