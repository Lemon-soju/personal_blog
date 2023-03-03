package com.lemonSoju.blog.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class DeletePostRequestDto {
    private List<Long> checkedInputs;

    @Builder
    public DeletePostRequestDto(List<Long> checkedInputs) {
        this.checkedInputs = checkedInputs;
    }
}