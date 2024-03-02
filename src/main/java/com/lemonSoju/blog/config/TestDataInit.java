package com.lemonSoju.blog.config;

import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.repository.MemberDataRepository;
import com.lemonSoju.blog.repository.PostDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public class TestDataInit {
    private final PostDataRepository postDataRepository;
    private final MemberDataRepository memberDataRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        Member admin = memberDataRepository.save(Member.builder()
                .uid("admin")
                .pwd("1q2w3e4r1!")
                .name("관리자")
                .authority("ROLE_ADMIN")
                .build());

        Member user01 = memberDataRepository.save(Member.builder()
                .uid("user01")
                .pwd("1q2w3e4r1!")
                .name("사용자1")
                .authority("ROLE_USER")
                .build());

        Post post01 = postDataRepository.save(Post.builder()
                .title("제목1")
                .content("내용1 \n <img src=\"https://blog.kakaocdn.net/dn/cZBwDS/btr6K3vDCha/kAOXAoDBMhOFjrrwcdPr8k/img.jpg\">")
                .createDate(LocalDateTime.now())
                .imagePreview("https://blog.kakaocdn.net/dn/cZBwDS/btr6K3vDCha/kAOXAoDBMhOFjrrwcdPr8k/img.jpg")
                .writer(admin)
                .build());

        Post post02 = postDataRepository.save(Post.builder()
                .title("제목2")
                .content("내용2 \n <img src=\"https://blog.kakaocdn.net/dn/cACX1n/btsE0s4HohB/nfYtZ1gkKy2cv6ChiMnAKk/img.jpg\">")
                .createDate(LocalDateTime.now())
                .imagePreview("https://blog.kakaocdn.net/dn/cACX1n/btsE0s4HohB/nfYtZ1gkKy2cv6ChiMnAKk/img.jpg")
                .writer(user01)
                .build());
    }
}
