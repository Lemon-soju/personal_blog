package com.lemonSoju.blog.service;


import com.lemonSoju.blog.domain.Like;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;
import com.lemonSoju.blog.dto.response.MemberLoginResponseDto;
import com.lemonSoju.blog.dto.response.MemberSignUpResponseDto;
import com.lemonSoju.blog.dto.response.OkResponseDto;
import com.lemonSoju.blog.exception.MemberDuplicateException;
import com.lemonSoju.blog.exception.MemberNonExistException;
import com.lemonSoju.blog.repository.LikeDataRepository;
import com.lemonSoju.blog.repository.MemberDataRepository;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberDataRepository memberDataRepository;
    private final PostDataRepository postDataRepository;
    private final LikeDataRepository likeDataRepository;
    private final JwtService jwtService;

    @Transactional
    public MemberSignUpResponseDto join(MemberSignUpRequestDto memberSignUpRequestDto) {
        validateDuplicateMember(memberSignUpRequestDto);
        Member member = Member.builder()
                .uid(memberSignUpRequestDto.getUid())
                .pwd(memberSignUpRequestDto.getPwd())
                .name(memberSignUpRequestDto.getName())
                .authority("ROLE_USER").build();
        memberDataRepository.save(member);
        MemberSignUpResponseDto memberSignUpResponseDto = MemberSignUpResponseDto.builder()
                .uid(member.getUid()).build();
        return memberSignUpResponseDto;
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto memberLoginRequestDto) {
        List<Member> findMembers = memberDataRepository.findByUid(memberLoginRequestDto.getUid())
                .stream()
                .filter(m -> m.getPwd().equals(memberLoginRequestDto.getPwd()))
                .collect(Collectors.toList());
        if (findMembers.isEmpty()) {
            throw new MemberNonExistException();
        }
        Member findMember = findMembers.get(0);
        String jws = jwtService.createAccessToken(findMember);
        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .uid(findMember.getUid())
                .accessToken(jws)
                .build();
        return memberLoginResponseDto;
    }

    public MemberLoginResponseDto refreshToken(String accessToken) {
        Member findMember = jwtService.findMemberByToken(accessToken);
        String jws = jwtService.createAccessToken(findMember);
        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .uid(findMember.getUid())
                .accessToken(jws)
                .build();
        return memberLoginResponseDto;
    }

    private void validateDuplicateMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        Optional<Member> findMember = memberDataRepository.findByUid(memberSignUpRequestDto.getUid());
        if (!findMember.isEmpty()) {
            throw new MemberDuplicateException();
        }
    }

    @Transactional
    public ResponseEntity<OkResponseDto> createLike(Long postId, String accessToken) {
        Member findMember = jwtService.findMemberByToken(accessToken);
        Post findPost = postDataRepository.findById(postId).get();
        Like like = Like.builder().build();
        like.addMemberAndPost(findPost, findMember);
        likeDataRepository.save(like);
        return ResponseEntity.ok(new OkResponseDto());
    }
}