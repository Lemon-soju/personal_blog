package com.lemonSoju.blog.controller;

import com.lemonSoju.blog.dto.response.ErrorResponseDto;
import com.lemonSoju.blog.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customException(CustomException e) {
        int statusCode = e.getStatusCode();

        ErrorResponseDto body = ErrorResponseDto.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponseDto> response = ResponseEntity
                .status(statusCode)
                .body(body);

        return response;
    }
}
