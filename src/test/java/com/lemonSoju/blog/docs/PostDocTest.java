package com.lemonSoju.blog.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemonSoju.blog.domain.Member;
import com.lemonSoju.blog.dto.request.DeletePostRequestDto;
import com.lemonSoju.blog.dto.request.PostWriteRequestDto;
import com.lemonSoju.blog.repository.MemerDataRepository;
import com.lemonSoju.blog.service.PostService;
import com.lemonSoju.blog.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemerDataRepository memerDataRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";

//    @Test
//    @DisplayName("글쓰기")
//    void postWrite() throws Exception {
//
//        // given
//        String jwt = createToken();
//
//        PostWriteRequestDto request = PostWriteRequestDto.builder()
//                .title("test01")
//                .content("test01-content")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);
//
//        // expected
//        mockMvc.perform(post("/post/write")
//                        .contentType(APPLICATION_JSON)
//                        .accept(APPLICATION_JSON)
//                        .header("accessToken", jwt)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("post-write",
//                        requestFields(
//                                fieldWithPath("title").description("제목"),
//                                fieldWithPath("content").description("내용")
//                        ),
//                        responseFields(
//                                fieldWithPath("postId").description("글 번호")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("글 전체 불러오기")
//    void getPosts() throws Exception {
//        // expected
//        mockMvc.perform(get("/post")
//                        .contentType(APPLICATION_JSON)
//                        .accept(APPLICATION_JSON)
//                ).andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("get-posts",
//                        responseFields(
//                                fieldWithPath("[].postId").description("글 번호"),
//                                fieldWithPath("[].title").description("글 제목"),
//                                fieldWithPath("[].content").description("글 내용"),
//                                fieldWithPath("[].writer").description("글쓴이"),
//                                fieldWithPath("[].createDate").description("생성시각")
//                        )
//                ));
//    }
//
//
//    @Test
//    @DisplayName("글 삭제")
//    void postDelete() throws Exception {
//
//        // given
//        String jwt = createToken();
//        List<Long> deleteList = List.of(6L, 8L);
//
//        DeletePostRequestDto request = DeletePostRequestDto.builder()
//                .checkedInputs(deleteList)
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);
//
//        // expected
//        mockMvc.perform(post("/member/post/delete")
//                        .contentType(APPLICATION_JSON)
//                        .accept(APPLICATION_JSON)
//                        .header("accessToken", jwt)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("post-delete",
//                        requestFields(
//                                fieldWithPath("checkedInputs").description("삭제 리스트")
//                        )
//                ));
//    }
//
//    private String createToken() {
//        Member findMember = memerDataRepository.findByUid("user01").get();
//
//        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode((KEY)));
//
//        // jwt 설정
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis()); // 만료기간 1일
//
//        String jws = Jwts.builder()
//                .setSubject(findMember.getUid())
//                .setIssuedAt(now) // 발급시간(iat)
//                .setExpiration(expiration) // 만료시간(exp)
//                .signWith(key) // 사용자 uid
//                .compact();
//        return jws;
//    }
}