package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.response.MemberLoginResponseDto;
import com.lemonSoju.blog.dto.response.MemberSignUpResponseDto;
import com.lemonSoju.blog.repository.MemberDataRepository;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberDataRepository memberDataRepository;
    private final JwtService jwtService;
    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";


    /**
     * 회원 가입
     */
    @Transactional
    public MemberSignUpResponseDto join(MemberSignUpRequestDto memberSignUpRequestDto) {
        validateDuplicateMember(memberSignUpRequestDto);
        Member savedMember = memberDataRepository.save(createMember(memberSignUpRequestDto));
        MemberSignUpResponseDto memberSignUpResponseDto = MemberSignUpResponseDto.builder()
                .uid(savedMember.getUid())
                .build();
        return memberSignUpResponseDto;
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {

        List<Member> findMember = memberDataRepository.findByUid(memberLoginRequestDto.getUid())
                .stream().filter(m -> m.getPwd().equals(memberLoginRequestDto.getPwd())).collect(Collectors.toList());
        if(findMember.size() != 1) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }

        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode((KEY)));

        // jwt 설정
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis()); // 만료기간 30분

        String jws = Jwts.builder()
                .setSubject(findMember.get(0).getUid())
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .signWith(key) // 사용자 uid
                .compact();

        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .uid(findMember.get(0).getUid())
                .accessToken(jws)
                .build();
        return memberLoginResponseDto;
    }

    public MemberLoginResponseDto refreshToken(HttpHeaders request) {
        Member findMember = jwtService.findMemberByToken(request);
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode((KEY)));

        // jwt 설정
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofMinutes(30).toMillis()); // 만료기간 30분

        String jws = Jwts.builder()
                .setSubject(findMember.getUid())
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .signWith(key) // 사용자 uid
                .compact();

        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .uid(findMember.getUid())
                .accessToken(jws)
                .build();
        return memberLoginResponseDto;
    }
    private void validateDuplicateMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        Optional<Member> findMembers = memberDataRepository.findByUid(memberSignUpRequestDto.getUid());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("Already Existing Member");
        }
    }

    /**
     * Member 객체 생성
     */
    public Member createMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        Member member = Member.builder()
                .uid(memberSignUpRequestDto.getUid())
                .pwd(memberSignUpRequestDto.getPwd())
                .name(memberSignUpRequestDto.getName())
                .authority("ROLE_USER")
                .build();
        return member;
    }
}