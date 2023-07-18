package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.response.MemberLoginResponseDto;
import com.lemonSoju.blog.dto.response.MemberSignUpResponseDto;
import com.lemonSoju.blog.repository.MemberDataRepository;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberDataRepository memberDataRepository;
    private final JwtService jwtService;
    private final PostService postService;

    @Transactional
    public MemberSignUpResponseDto join(MemberSignUpRequestDto memberSignUpRequestDto) {
        validateDuplicateMember(memberSignUpRequestDto);
        Member member = Member.builder().uid(memberSignUpRequestDto.getUid()).pwd(memberSignUpRequestDto.getPwd()).name(memberSignUpRequestDto.getName()).authority("ROLE_USER").build();
        memberDataRepository.save(member);
        MemberSignUpResponseDto memberSignUpResponseDto = MemberSignUpResponseDto.builder().uid(member.getUid()).build();
        return memberSignUpResponseDto;
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {
        Member findMember = memberDataRepository.findByUid(memberLoginRequestDto.getUid()).stream().filter(m -> m.getPwd().equals(memberLoginRequestDto.getPwd())).collect(Collectors.toList()).get(0);
        if (findMember.equals(null)) {
            throw new IllegalStateException("존재하지 않는 회원입니다");
        }
        String jws = jwtService.createAccessToken(findMember);
        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder().uid(findMember.getUid()).accessToken(jws).build();
        return memberLoginResponseDto;
    }

    public MemberLoginResponseDto refreshToken(String accessToken) {
        Member findMember = jwtService.findMemberByToken(accessToken);
        String jws = jwtService.createAccessToken(findMember);
        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder().uid(findMember.getUid()).accessToken(jws).build();
        return memberLoginResponseDto;
    }

    private void validateDuplicateMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        Optional<Member> findMembers = memberDataRepository.findByUid(memberSignUpRequestDto.getUid());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("Already Existing Member");
        }
    }
}