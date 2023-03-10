package com.lemonSoju.blog;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.repository.PostDataRepository;
import com.lemonSoju.blog.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final PostDataRepository postDataRepository;
    private final UserDataRepository userDataRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        User user01 = createAdmin();
        User user02 = createUser();
        Post post01 = createPost02(user01);
        Post post02 = createPost03(user01);
        Post post03 = createPost(user01);
        Post post04 = createPost(user02);
        Post post05 = createPost04(user01);
        Post post06 = createPost(user02);
        Post post07 = createPost(user01);
        Post post08 = createPost(user02);
        Post post09 = createPost(user01);
        Post post10 = createPost(user02);
        Post post11 = createPost(user01);
        Post post12 = createPost(user02);
        Post post13 = createPost(user01);
        Post post14 = createPost(user02);
        Post post15 = createPost(user02);
        Post post16 = createPost(user01);
        Post post17 = createPost(user01);
        Post post18 = createPost(user01);
        Post post19 = createPost(user02);
        Post post20 = createPost(user01);
        Post post21 = createPost(user01);
        Post post22 = createPost(user02);
        Post post23 = createPost(user01);
        Post post24 = createPost(user02);
        Post post25 = createPost(user02);
        Post post26 = createPost(user01);
        Post post27 = createPost(user01);
        Post post28 = createPost(user01);
        Post post29 = createPost(user02);
        Post post30 = createPost(user01);
    }

    private User createAdmin() {
        User user = User.builder()
                .uid("admin")
                .pwd("1q2w3e4r1!")
                .name("lemonSoju")
                .authority("ROLE_ADMIN")
                .build();
        userDataRepository.save(user);
        return user;
    }

    private User createUser() {
        User user = User.builder()
                .uid("user02")
                .pwd("1q2w3e4r1!")
                .name("James")
                .authority("ROLE_USER")
                .build();
        userDataRepository.save(user);
        return user;
    }

    private Post createPost(User user) {
        Post post = Post.builder()
                .title("title1")
                .content("content1")
                .writer(user)
                .createDate(LocalDateTime.now())
                .build();
        return postDataRepository.save(post);
    }

    private Post createPost02(User user) {
        Post post = Post.builder()
                .title("개발자라면 꼭 알아야 할 프로그래밍 언어 5가지")
                .content("현재 개발 시장에서 인기 있는 프로그래밍 언어를 선정하고, 각 언어의 특징과 장단점을 비교하며, 개발자로서 어떤 언어를 배우면 유용한지에 대해 제안합니다. 예를 들어, Python의 간결한 문법과 다양한 라이브러리를 활용하여 빠르게 개발을 진행할 수 있다는 점이나, Java의 안정성과 대규모 프로젝트에 적합하다는 점 등을 소개합니다.")
                .writer(user)
                .createDate(LocalDateTime.now())
                .build();
        return postDataRepository.save(post);
    }

    private Post createPost03(User user) {
        Post post = Post.builder()
                .title("내가 경험한 세계 여행 TOP 5")
                .content("여행 중 방문한 나라와 그 나라에서 경험한 색다른 문화와 음식, 관광지 등을 소개하며 추억에 남는 순위권 5개의 여행을 공유합니다. 예를 들어, 일본에서 찾은 마음의 평화를 느낄 수 있는 절이나 프랑스에서 맛보았던 진짜 마카롱 등의 이야기를 공유합니다. 이를 통해 독자들은 혹시 여행 계획을 세우고 있다면 도움을 받을 수 있습니다.")
                .writer(user)
                .createDate(LocalDateTime.now())
                .build();
        return postDataRepository.save(post);
    }

    private Post createPost04(User user) {
        Post post = Post.builder()
                .title("일주일만에 몸매 변화를 만들어낸 루틴 5가지")
                .content("일주일 동안 지속 가능한 운동 루틴을 소개하며, 운동에 대한 팁과 지식도 함께 제공합니다. 예를 들어, 유산소 운동과 근력 운동을 적절하게 조합하여 다이어트와 근력 강화를 동시에 이룰 수 있는 방법, 운동 전과 후의 스트레칭의 중요성 등을 설명합니다. 이를 통해 독자들은 몸매 변화를 만들어내기 위해 필요한 정보와 방법을 얻을 수 있습니다.")
                .writer(user)
                .createDate(LocalDateTime.now())
                .build();
        return postDataRepository.save(post);
    }
}