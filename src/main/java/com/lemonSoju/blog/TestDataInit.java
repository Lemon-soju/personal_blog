package com.lemonSoju.blog;

import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.domain.User;
import com.lemonSoju.blog.dto.request.UserSignUpRequestDto;
import com.lemonSoju.blog.repository.PostDataRepository;
import com.lemonSoju.blog.repository.UserDataRepository;
import com.lemonSoju.blog.service.PostService;
import com.lemonSoju.blog.service.UserService;
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

        User user01 = createUser();
        Post post01 = createPost(user01);
        Post post02 = createPost(user01);
        Post post03 = createPost(user01);
        Post post04 = createPost(user01);
        Post post05 = createPost(user01);

    }

    private User createUser() {
        User user = User.builder()
                .uid("admin")
                .pwd("1q2w3e4r1!")
                .name("lemonSoju")
                .authority("ROLE_ADMIN")
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