package com.lemonSoju.blog.service;

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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";
    private final UserDataRepository userDataRepository;


    public boolean authenticateToken(HttpServletRequest request) {
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
                throw new Unauthorized();
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
        return false;
    }

    /**
     * 현재 로그인한 사용자 정보가 필요할 때
     */
    public User findUserByToken(HttpServletRequest request) {
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

            User findUser = userDataRepository.findByUid(claims.getBody().getSubject()).get();
            return findUser;

        } catch (JwtException e) {
            throw new Unauthorized();
        } catch (NoSuchElementException e){
            throw new NonExistUser();
        }
    }
}
