package com.lemonSoju.blog.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinRequestDto {

    @NotEmpty(message = "회원 id는 필수입니다.")
    private String uid;
    @NotEmpty(message = "회원 비밀번호는 필수입니다.")
    private String pwd;
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    @Builder
    public UserJoinRequestDto(String uid, String pwd, String name) {
        this.uid = uid;
        this.pwd = pwd;
        this.name = name;
    }
}