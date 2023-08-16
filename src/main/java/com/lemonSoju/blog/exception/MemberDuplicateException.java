package com.lemonSoju.blog.exception;

public class MemberDuplicateException extends CustomException{
    private static final String MESSAGE = "이미 존재하는 사용자입니당.";

    public MemberDuplicateException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
