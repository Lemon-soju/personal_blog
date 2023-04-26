package com.lemonSoju.blog.exception;

public class NonExistMember extends CustomException{

    private static final String MESSAGE = "존재하지 않는 사용자압니다.";

    public NonExistMember() { super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 401;
    }
}
