package com.lemonSoju.blog.config;

import com.lemonSoju.blog.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private static final String KEY = "ryszg5rrIOkU3sPAKhsPuoLIXcJ7RX6O5N/StkVmzls=";
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 동작");
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 동작");

        String jws = webRequest.getHeader("accessToken");

        if (jws == null || jws.equals("")) {
            throw new Unauthorized();
        }

        UserSession userSession = new UserSession();
        userSession.name = jws;

        byte[] decodeKey = Base64.decodeBase64(KEY);

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodeKey)
                    .build()
                    .parseClaimsJws(jws);

        } catch (JwtException e) {
            throw new Unauthorized();
        }

        return userSession;
    }
}
