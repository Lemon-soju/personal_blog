package com.lemonSoju.blog.exception;

public class MemberNonExistException extends CustomException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public MemberNonExistException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
