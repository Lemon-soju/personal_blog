package com.lemonSoju.blog.config;

import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.exception.NonExistUser;
import com.lemonSoju.blog.exception.Unauthorized;
import com.lemonSoju.blog.repository.UserDataRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.NoSuchElementException;


/**
 * resolver 동작하면 삭제할 예정
 */
@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";
    private final UserDataRepository userDataRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle 동작");

        String accessToken = request.getHeader("accessToken");
        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        byte[] decodeKey = Base64.decodeBase64(KEY);
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodeKey)
                    .build()
                    .parseClaimsJws(accessToken);
            log.info("claims 생성");

            Date now = new Date();
            if(!now.before(claims.getBody().getExpiration())) {

            }

            // 사용자 존재여부 검사
            User findUser = userDataRepository.findByUid(claims.getBody().getSubject()).get();
            if(!findUser.equals(null)) {
                return true;
            }
        } catch (JwtException e) {
            throw new Unauthorized();
        } catch (NoSuchElementException e){
            throw new NonExistUser();
        }

        throw new Unauthorized();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
