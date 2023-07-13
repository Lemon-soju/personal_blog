package com.lemonSoju.blog.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "http://3.35.179.185", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@Transactional
public class AuthDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Utility utility;

    @Test
    @DisplayName("토큰 갱신")
    void refreshToken() throws Exception {

        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accessToken", utility.mockJwt(utility.mockSignup("test01")));

        // expected
        mockMvc.perform(get("/auth/refreshToken")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-refreshToken",
                        responseFields(
                                fieldWithPath("uid").description("사용자 ID"),
                                fieldWithPath("accessToken").description("JWT 토큰")
                        )
                ));
    }
}
