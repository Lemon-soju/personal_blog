package com.lemonSoju.blog.exception;

public class NonExistUser extends CustomException{

    private static final String MESSAGE = "존재하지 않는 사용자압니다.";

    public NonExistUser() { super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 401;
    }
}
