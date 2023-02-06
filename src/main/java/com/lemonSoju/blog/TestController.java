package com.lemonSoju.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @PostMapping("/test")
    public Long test(@RequestBody Long num) {
        log.info("접속시도");
        return num;
    }
}
