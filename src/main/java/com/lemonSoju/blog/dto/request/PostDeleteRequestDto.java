package com.lemonSoju.blog.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PostDeleteRequestDto {
    private List<Long> checkedInputs;
}