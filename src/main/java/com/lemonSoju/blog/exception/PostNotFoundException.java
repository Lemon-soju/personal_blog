package com.lemonSoju.blog.exception;

public class PostNotFoundException extends CustomException {
    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
