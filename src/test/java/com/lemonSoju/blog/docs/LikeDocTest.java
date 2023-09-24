package com.lemonSoju.blog.docs;

import com.lemonSoju.blog.domain.Like;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "http://3.35.179.185", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@Transactional
public class LikeDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Utility utility;

    @Test
    @DisplayName("찜하기 생성")
    void createLike() throws Exception {
        // given
        Member member = utility.mockSignup("test01");
        utility.mockCreatePost(member);
        String jwt = utility.mockJwt(member);
        Long postId = utility.mockCreatePost(member).getId();

        // expected
        mockMvc.perform(post("/like/post/{postId}", postId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header("accessToken", jwt))
                .andExpect(status().isOk())
                .andDo(document("like-create", responseFields(
                        fieldWithPath("message").description("성공"))
                ));
    }

    @Test
    @DisplayName("찜하기 삭제")
    void deleteLike() throws Exception {
        // given
        Member member = utility.mockSignup("test01");
        Post post = utility.mockCreatePost(member);
        String jwt = utility.mockJwt(member);
        Like like = new Like();
        like.addMemberAndPost(post, member);

        // expected
        mockMvc.perform(delete("/like/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header("accessToken", jwt))
                .andExpect(status().isOk())
                .andDo(document("like-delete", responseFields(
                        fieldWithPath("message").description("성공"))
                ));
    }
}