package com.nurettinyakit.restexample.gateway.controller;

import com.nurettinyakit.restexample.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nurettinyakit.restexample.domain.ConstantsUtil.USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WelcomeController {

    private final UseCase<String, String> getWelcome;
    private int count = 0;

    @GetMapping(value = "/welcome")
    public String welcome(@RequestHeader(USER_ID) final String userId, @RequestParam String name) {
        log.info("Welcoming Customer {}", userId);
        return getWelcome.execute(name);
    }

    @GetMapping(value = "/counter")
    public String counter() {
        count++;
        log.info("Count : {}", count);
        return "" + count;
    }
}
