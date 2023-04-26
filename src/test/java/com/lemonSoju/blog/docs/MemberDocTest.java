package com.lemonSoju.blog.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemonSoju.blog.dto.request.MemberLoginRequestDto;
import com.lemonSoju.blog.dto.request.MemberSignUpRequestDto;
import com.lemonSoju.blog.service.UserService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "http://3.35.179.185", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@Transactional
public class MemberDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입")
    void signup() throws Exception {

        // given
        MemberSignUpRequestDto request = MemberSignUpRequestDto.builder()
                .uid("아이디입니다.")
                .pwd("패스워드입니다.")
                .name("이름입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-signup",
                        requestFields(
                                fieldWithPath("uid").description("아이디"),
                                fieldWithPath("pwd").description("패스워드"),
                                fieldWithPath("name").description("이름")
                        ),
                        responseFields(
                                fieldWithPath("uid").description("사용자 ID")
                        )
                ));
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {

        // given
        MemberLoginRequestDto request = MemberLoginRequestDto.builder()
                .uid("user01")
                .pwd("1q2w3e4r1!")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-login",
                        requestFields(
                                fieldWithPath("uid").description("아이디"),
                                fieldWithPath("pwd").description("패스워드")
                        ),
                        responseFields(
                                fieldWithPath("uid").description("사용자 ID"),
                                fieldWithPath("accessToken").description("JWT 토큰")
                        )
                ));
    }
}
