package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.exception.JwtTokenNull;
import com.lemonSoju.blog.exception.NonExistUser;
import com.lemonSoju.blog.exception.Unauthorized;
import com.lemonSoju.blog.repository.MemerDataRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";
    private final MemerDataRepository memerDataRepository;


    public boolean authenticateToken(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null || accessToken.equals("")) {
            throw new JwtTokenNull();
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
            Member findMember = memerDataRepository.findByUid(claims.getBody().getSubject()).get();
            if(!findMember.equals(null)) {
                log.info("일치하는 사용자 존재");
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
    public Member findUserByToken(HttpHeaders request) {
        String accessToken = request.getFirst("accessToken");
        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        byte[] decodeKey = Base64.decodeBase64(KEY);
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodeKey)
                    .build()
                    .parseClaimsJws(accessToken);

            Member findMember = memerDataRepository.findByUid(claims.getBody().getSubject()).get();
            return findMember;

        } catch (JwtException e) {
            throw new Unauthorized();
        } catch (NoSuchElementException e){
            throw new NonExistUser();
        }
    }
}
