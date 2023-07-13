package com.lemonSoju.blog.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.domain.Post;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.service.PostService;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "http://3.35.179.185", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@Transactional
public class PostDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Utility utility;

    @Test
    @DisplayName("글쓰기")
    void postWrite() throws Exception {
        // given
        Member member = utility.mockSignup("test01");
        String jwt = utility.mockJwt(member);

        PostWriteRequestDto request = PostWriteRequestDto.builder()
                .title("test title")
                .content("test content")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/post")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header("accessToken", jwt)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-write",
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("글 번호")
                        )
                ));
    }

    @Test
    @DisplayName("글 전체 불러오기")
    void getPosts() throws Exception {
        // expected
        mockMvc.perform(get("/post")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-posts",
                        responseFields(
                                fieldWithPath("[].postId").description("글 번호"),
                                fieldWithPath("[].title").description("글 제목"),
                                fieldWithPath("[].writer").description("글쓴이"),
                                fieldWithPath("[].createDate").description("생성시각"),
                                fieldWithPath("[].imagePreview").description("이미지 미리보기").optional()
                        )
                ));
    }

    @Test
    @DisplayName("글 삭제")
    void postDelete() throws Exception {

        // given
        Member member = utility.mockSignup("test01");
        String jwt = utility.mockJwt(member);
        Long postId01 = utility.mockCreatePost(member).getId();
        Long postId02 = utility.mockCreatePost(member).getId();
        List<Long> deleteList = List.of(postId01, postId02);

        DeletePostRequestDto request = DeletePostRequestDto.builder()
                .checkedInputs(deleteList)
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/post/delete")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header("accessToken", jwt)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-delete",
                        requestFields(
                                fieldWithPath("checkedInputs").description("삭제 리스트")
                        )
                ));
    }
}