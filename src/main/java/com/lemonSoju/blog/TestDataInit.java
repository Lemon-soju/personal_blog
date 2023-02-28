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
        Post post01 = createPost(user01);
        Post post02 = createPost(user01);
        Post post03 = createPost(user01);
        Post post04 = createPost(user02);
        Post post05 = createPost(user01);
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
}