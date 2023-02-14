package com.lemonSoju.blog.exception;

public class TokenExpired extends CustomException{

    private static final String MESSAGE = "토큰이 만료되었습니다.";

    public TokenExpired() { super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 401;
    }
}
