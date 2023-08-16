package com.lemonSoju.blog.service;

import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.exception.JwtTokenNullException;
import com.lemonSoju.blog.exception.MemberNonExistException;
import com.lemonSoju.blog.exception.UnauthorizedException;
import com.lemonSoju.blog.repository.MemberDataRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String ACCESS_TOKEN = "accessToken";
    private final MemberDataRepository memberDataRepository;
    @Value("${jwt.key}")
    private String KEY;

    public String getJwtKey() {
        return KEY;
    }

    public String createAccessToken(Member findMember) {
        Key key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode((KEY)));
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis());
        String jws = Jwts.builder()
                .setSubject(findMember.getUid())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        return jws;
    }

    public boolean authenticateToken(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null || accessToken.equals("")) {
            throw new JwtTokenNullException();
        }
        byte[] decodeKey = Base64.decodeBase64(getJwtKey());

        // claims 생성
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(decodeKey)
                .build()
                .parseClaimsJws(accessToken);

        Date now = new Date();
        if (!now.before(claims.getBody().getExpiration())) {
            throw new UnauthorizedException();
        }

        // 사용자 존재여부 검사
        Optional<Member> findMember = memberDataRepository.findByUid(claims.getBody().getSubject());
        if (!findMember.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 현재 로그인한 사용자 정보가 필요할 때
     */
    public Member findMemberByToken(String accessToken) {
        if (accessToken == null || accessToken.equals("")) {
            throw new UnauthorizedException();
        }

        byte[] decodeKey = Base64.decodeBase64(KEY);
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(decodeKey)
                .build()
                .parseClaimsJws(accessToken);

        Optional<Member> findMember = memberDataRepository.findByUid(claims.getBody().getSubject());
        if (findMember.isEmpty()) {
            new MemberNonExistException();
        }
        return findMember.get();
    }
}
